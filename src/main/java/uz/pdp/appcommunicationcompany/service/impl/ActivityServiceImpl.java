package uz.pdp.appcommunicationcompany.service.impl;

import com.convertapi.client.Config;
import com.convertapi.client.ConversionResultFile;
import com.convertapi.client.ConvertApi;
import com.convertapi.client.Param;
import com.google.gson.Gson;
import lombok.RequiredArgsConstructor;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.xssf.usermodel.*;
import org.springframework.stereotype.Service;
import org.springframework.util.FileCopyUtils;
import uz.pdp.appcommunicationcompany.domain.*;
import uz.pdp.appcommunicationcompany.enums.ServiceCategoryEnum;
import uz.pdp.appcommunicationcompany.helper.CurrentUser;
import uz.pdp.appcommunicationcompany.mapper.MapstructMapper;
import uz.pdp.appcommunicationcompany.model.dto.create.ActivityDto;
import uz.pdp.appcommunicationcompany.model.resp.ApiResponse;
import uz.pdp.appcommunicationcompany.repository.ActivityRepo;
import uz.pdp.appcommunicationcompany.repository.ActivityTypeRepo;
import uz.pdp.appcommunicationcompany.repository.SubscriberRepo;
import uz.pdp.appcommunicationcompany.service.ActivityService;

import javax.servlet.http.HttpServletResponse;
import javax.transaction.Transactional;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.*;

@Service
@Transactional
@RequiredArgsConstructor
public class ActivityServiceImpl implements ActivityService {
    private final ActivityRepo activityRepo;

    private final MapstructMapper mapper;

    private final ActivityTypeRepo activityTypeRepo;

    private final SimCardServiceImpl simCardService;

    private final SubscriberRepo subscriberRepo;

    private final CurrentUser currentUser;

    private final ActivityAndActivityType activityAndActivityType;

    @Override
    public ApiResponse getActivities() {
        return new ApiResponse("OK", true, mapper.toActivityDto(activityRepo.findAll()));
    }

    @Override
    public ApiResponse getActivity(Long id) {
        Optional<Activity> optionalActivity = activityRepo.findById(id);
        return optionalActivity.map(activity -> new ApiResponse("OK", true, mapper.toActivityDto(activity))).orElseGet(() -> new ApiResponse("Activity not found", false));
    }

    @Override
    public ApiResponse addActivity(ActivityDto dto) {
        try {
            ApiResponse apiResponse = simCardService.getSubscriberFromNumber(dto.getFromNumber());
            if (!apiResponse.isStatus())
                return new ApiResponse("Subscriber not found", false);
            Subscriber from = (Subscriber) apiResponse.getObject();

            if (from.getSimCard().getBalance() > 0) {
                ActivityType activityType;
                Optional<ActivityType> optionalActivityType = activityTypeRepo.findByName(ServiceCategoryEnum.valueOf(dto.getServiceCategory()).name());
                if (optionalActivityType.isPresent()) {
                    activityType = optionalActivityType.get();
                } else {
                    activityType = new ActivityType();
                    activityType.setName(dto.getServiceCategory());
                    activityType = activityTypeRepo.save(activityType);
                }

                Map<String, Object> details = new HashMap<>();
                details.put("activityName", activityType.getName());
                details.put("from", dto.getFromNumber());
                details.put("to", dto.getToNumber());

                long amount = 0;
                for (int i = 0; i < dto.getAmount(); i++) {
                    if (!simCardService.useActivity(from, activityType, 1))
                        break;
                    amount++;
                }
                subscriberRepo.save(from);
                details.put("amount", amount);
                activityAndActivityType.createActivity(from, activityType, details);
                return new ApiResponse("Created", true);
            }
            return new ApiResponse("Please top up the account", false);
        } catch (Exception e) {
            return new ApiResponse("Wrong format category type (Use SMS, CALL_NETWORK, CALL_OUT_NETWORK, MB)", false);
        }
    }

    @Override
    public ApiResponse getMyActivityExcel(String number, String from, String to, HttpServletResponse response) {
        try {
            LocalDateTime start = LocalDateTime.of(LocalDate.parse(from), LocalTime.of(0, 0, 0));
            LocalDateTime end = LocalDateTime.of(LocalDate.parse(to), LocalTime.of(0, 0, 0));
            Client client = currentUser.getCurrentClient();
            ApiResponse api = simCardService.getSubscriberFromNumber(number);
            if (!api.isStatus())
                return new ApiResponse("Subscriber not found", false);
            Subscriber subscriber = (Subscriber) api.getObject();
            if (!client.getSimCards().contains(subscriber.getSimCard()))
                return new ApiResponse("You don't have access to see this information", false);

            List<Activity> activities = activityRepo.findAllBySubscriberIdAndCreatedAtBetween(subscriber.getId(), start, end);

            File file = createExcel(activities, subscriber);

            response.setHeader("Content-Disposition", "attachment; filename=\"" + file.getName() + "\"");
            response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");

            FileCopyUtils.copy(new FileInputStream("src/main/resources/reports/" + file.getName()), response.getOutputStream());
            file.delete();
            return new ApiResponse("OK", true);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return new ApiResponse("Date format is not correct. (Use YYYY-MM-DD", false);
        }
    }

    @Override
    public ApiResponse getMyActivityPdf(String number, String from, String to, HttpServletResponse response) {
        try {
            LocalDateTime start = LocalDateTime.of(LocalDate.parse(from), LocalTime.of(0, 0, 0));
            LocalDateTime end = LocalDateTime.of(LocalDate.parse(to), LocalTime.of(0, 0, 0));
            Client client = currentUser.getCurrentClient();
            ApiResponse api = simCardService.getSubscriberFromNumber(number);
            if (!api.isStatus())
                return new ApiResponse("Subscriber not found", false);
            Subscriber subscriber = (Subscriber) api.getObject();
            if (!client.getSimCards().contains(subscriber.getSimCard()))
                return new ApiResponse("You don't have access to see this information", false);

            List<Activity> activities = activityRepo.findAllBySubscriberIdAndCreatedAtBetween(subscriber.getId(), start, end);

            File file = createExcel(activities, subscriber);

            Config.setDefaultSecret("5WdUicLnHCak91LD");
            ConversionResultFile file1 = ConvertApi.convert("xlsx", "pdf",
                    new Param("file", Paths.get(file.getPath()))).get().getFile(0);

            URL url = new URL(file1.getUrl());
            URLConnection urlConnection = url.openConnection();

            response.setHeader("Content-Disposition", "attachment; filename=\"" + file1.getName() + "\"");
            response.setContentType("application/pdf");


            FileCopyUtils.copy(urlConnection.getInputStream(), response.getOutputStream());
            file.delete();
            return new ApiResponse("OK", true);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return new ApiResponse("Date format is not correct. (Use YYYY-MM-DD", false);
        }
    }

    public File createExcel(List<Activity> activities, Subscriber subscriber) {
        File file = new File("src/main/resources/reports/" + UUID.randomUUID() + ".xlsx");
        try (FileOutputStream outputStream = new FileOutputStream(file)) {
            file.createNewFile();
            XSSFWorkbook workbook = new XSSFWorkbook();

            //todo sheet goods
            XSSFSheet sheet = workbook.createSheet("Activities");
            XSSFCellStyle myStyle = workbook.createCellStyle();
            XSSFFont myFontBold = workbook.createFont();
            myFontBold.setBold(true);

            myStyle.setFont(myFontBold);
            myStyle.setBorderTop(BorderStyle.MEDIUM);
            myStyle.setBorderRight(BorderStyle.MEDIUM);
            myStyle.setBorderBottom(BorderStyle.MEDIUM);
            myStyle.setBorderLeft(BorderStyle.MEDIUM);

            XSSFCellStyle mySty = workbook.createCellStyle();
            XSSFFont myFont = workbook.createFont();

            mySty.setFont(myFont);
            mySty.setBorderTop(BorderStyle.MEDIUM);
            mySty.setBorderRight(BorderStyle.MEDIUM);
            mySty.setBorderBottom(BorderStyle.MEDIUM);
            mySty.setBorderLeft(BorderStyle.MEDIUM);

            XSSFRow row1 = sheet.createRow(0);
            Cell cell = row1.createCell(0);
            cell.setCellStyle(myStyle);
            cell.setCellValue("Subscriber number");
            cell = row1.createCell(1);
            cell.setCellStyle(myStyle);
            cell.setCellValue("Activity name");
            cell = row1.createCell(2);
            cell.setCellStyle(myStyle);
            cell.setCellValue("Amount");

            for (int i = 0; i < activities.size(); i++) {
                row1 = sheet.createRow(i + 1);

                cell = row1.createCell(0);
                cell.setCellStyle(mySty);
                cell.setCellValue(subscriber.getSimCard().getNumber().getFullNumber());
                cell = row1.createCell(1);
                cell.setCellStyle(mySty);
                cell.setCellValue(activities.get(i).getActivityType().getName());
                cell = row1.createCell(2);
                cell.setCellStyle(mySty);
                Map<String, Object> map = new Gson().fromJson(activities.get(i).getDetails(), Map.class);
                cell.setCellValue(Double.parseDouble(map.get("amount").toString()));
            }

            for (int i = 0; i <= row1.getLastCellNum(); i++) {
                sheet.setColumnWidth(i, 5000);
            }
            workbook.write(outputStream);

            return file;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }


}
