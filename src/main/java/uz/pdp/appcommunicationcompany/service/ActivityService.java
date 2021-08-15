package uz.pdp.appcommunicationcompany.service;

import uz.pdp.appcommunicationcompany.model.dto.create.ActivityDto;
import uz.pdp.appcommunicationcompany.model.resp.ApiResponse;

import javax.servlet.http.HttpServletResponse;

public interface ActivityService {
    ApiResponse getActivities();

    ApiResponse getActivity(Long id);

    ApiResponse addActivity(ActivityDto dto);

    ApiResponse getMyActivityExcel(String number, String from, String to, HttpServletResponse response);

    ApiResponse getMyActivityPdf(String number, String from, String to, HttpServletResponse response);
}
