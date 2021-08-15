package uz.pdp.appcommunicationcompany.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import uz.pdp.appcommunicationcompany.model.dto.create.ActivityDto;
import uz.pdp.appcommunicationcompany.model.resp.ApiResponse;
import uz.pdp.appcommunicationcompany.service.ActivityService;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import static uz.pdp.appcommunicationcompany.controller.ApiController.API_PATH;

@RestController
@RequestMapping(API_PATH + "/activity")
@RequiredArgsConstructor
public class ActivityController {
    private final ActivityService activityService;

    @GetMapping
    @PreAuthorize("hasAnyRole('DIRECTOR', 'BRANCH_DIRECOTR', 'NUMBER_MANAGER')")
    public ResponseEntity<?> getActivities() {
        ApiResponse apiResponse = activityService.getActivities();
        return ResponseEntity.status(apiResponse.isStatus() ? 200 : 400).body(apiResponse);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('DIRECTOR', 'BRANCH_DIRECOTR', 'NUMBER_MANAGER')")
    public ResponseEntity<?> getActivity(@PathVariable Long id) {
        ApiResponse apiResponse = activityService.getActivity(id);
        return ResponseEntity.status(apiResponse.isStatus() ? 200 : 400).body(apiResponse);
    }

    @PostMapping
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<?> addActivity(@Valid @RequestBody ActivityDto dto) {
        ApiResponse apiResponse = activityService.addActivity(dto);
        return ResponseEntity.status(apiResponse.isStatus() ? 201 : 400).body(apiResponse);
    }

    @GetMapping("/report/excel/{number}")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<?> getMyActivityExcel(@PathVariable String number, @RequestParam String from, @RequestParam String to, HttpServletResponse response) {
        ApiResponse apiResponse = activityService.getMyActivityExcel(number, from, to, response);
        return ResponseEntity.status(apiResponse.isStatus() ? 200 : 400).body(apiResponse);
    }

    @GetMapping("/report/pdf/{number}")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<?> getMyActivityPdf(@PathVariable String number, @RequestParam String from, @RequestParam String to, HttpServletResponse response) {
        ApiResponse apiResponse = activityService.getMyActivityPdf(number, from, to, response);
        return ResponseEntity.status(apiResponse.isStatus() ? 200 : 400).body(apiResponse);
    }
}
