package uz.pdp.appcommunicationcompany.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import uz.pdp.appcommunicationcompany.model.resp.ApiResponse;
import uz.pdp.appcommunicationcompany.service.SimCardService;

import static uz.pdp.appcommunicationcompany.controller.ApiController.API_PATH;

@RestController
@RequestMapping(API_PATH + "/sim/card")
@RequiredArgsConstructor
public class SimCardController {
    private final SimCardService simCardService;

    @GetMapping
    @PreAuthorize("hasAnyRole('DIRECTOR', 'NUMBER_MANAGER', 'BRANCH_DIRECTOR')")
    public ResponseEntity<?> getSimCards() {
        ApiResponse apiResponse = simCardService.getSimCards();
        return ResponseEntity.status(apiResponse.isStatus() ? 200 : 400).body(apiResponse);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('DIRECTOR', 'NUMBER_MANAGER', 'BRANCH_DIRECTOR')")
    public ResponseEntity<?> getSimCard(@PathVariable Long id) {
        ApiResponse apiResponse = simCardService.getSimCard(id);
        return ResponseEntity.status(apiResponse.isStatus() ? 200 : 400).body(apiResponse);
    }

    @GetMapping("report/income/{date1}/{date2}")
    @PreAuthorize("hasAnyRole('DIRECTOR', 'BRANCH_DIRECTOR')")
    public ResponseEntity<?> reportSimCards(@PathVariable String date1, @PathVariable String date2) {
        ApiResponse apiResponse = simCardService.reportSimCards(date1, date2);
        return ResponseEntity.status(apiResponse.isStatus() ? 200 : 400).body(apiResponse);
    }

    @GetMapping("report")
    @PreAuthorize("hasAnyRole('DIRECTOR', 'BRANCH_DIRECTOR')")
    public ResponseEntity<?> reportSimCards() {
        ApiResponse apiResponse = simCardService.reportSimCards();
        return ResponseEntity.status(apiResponse.isStatus() ? 200 : 400).body(apiResponse);

    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyRole('NUMBER_MANAGER', 'WORKER')")
    public ResponseEntity<?> deActivate(@PathVariable Long id) {
        ApiResponse apiResponse = simCardService.deActivate(id);
        return ResponseEntity.status(apiResponse.isStatus() ? 200 : 400).body(apiResponse);
    }
}
