package uz.pdp.appcommunicationcompany.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import uz.pdp.appcommunicationcompany.model.dto.create.ServiceDto;
import uz.pdp.appcommunicationcompany.model.resp.ApiResponse;
import uz.pdp.appcommunicationcompany.service.CommunicationService;

import javax.validation.Valid;

import static uz.pdp.appcommunicationcompany.controller.ApiController.API_PATH;

@RestController
@RequestMapping(API_PATH + "/service")
@RequiredArgsConstructor
public class ServiceController {
    private final CommunicationService communicationService;

    @GetMapping
    @PreAuthorize("hasAnyRole('WORKER', 'USER', 'NUMBER_MANAGER')")
    public ResponseEntity<?> getServices() {
        ApiResponse apiResponse = communicationService.getServices();
        return ResponseEntity.status(apiResponse.isStatus() ? 200 : 400).body(apiResponse);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('WORKER', 'USER', 'NUMBER_MANAGER')")
    public ResponseEntity<?> getService(@PathVariable Long id) {
        ApiResponse apiResponse = communicationService.getService(id);
        return ResponseEntity.status(apiResponse.isStatus() ? 200 : 400).body(apiResponse);
    }

    @PostMapping
    @PreAuthorize("hasRole('NUMBER_MANAGER')")
    public ResponseEntity<?> createService(@Valid @RequestBody ServiceDto dto) {
        ApiResponse apiResponse = communicationService.createService(dto);
        return ResponseEntity.status(apiResponse.isStatus() ? 201 : 400).body(apiResponse);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('NUMBER_MANAGER')")
    public ResponseEntity<?> editService(@Valid @RequestBody ServiceDto dto, @PathVariable Long id) {
        ApiResponse apiResponse = communicationService.editService(dto, id);
        return ResponseEntity.status(apiResponse.isStatus() ? 200 : 400).body(apiResponse);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('NUMBER_MANAGER')")
    public ResponseEntity<?> deleteService(@PathVariable Long id) {
        ApiResponse apiResponse = communicationService.deleteService(id);
        return ResponseEntity.status(apiResponse.isStatus() ? 200 : 400).body(apiResponse);
    }

    @GetMapping("/top")
    @PreAuthorize("hasRole('NUMBER_MANAGER')")
    public ResponseEntity<?> getTopServices() {
        ApiResponse apiResponse = communicationService.getTopServices();
        return ResponseEntity.status(apiResponse.isStatus() ? 200 : 400).body(apiResponse);
    }

    @GetMapping("/poor")
    @PreAuthorize("hasRole('NUMBER_MANAGER')")
    public ResponseEntity<?> getPoorServices() {
        ApiResponse apiResponse = communicationService.getPoorServices();
        return ResponseEntity.status(apiResponse.isStatus() ? 200 : 400).body(apiResponse);
    }

}
