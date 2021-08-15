package uz.pdp.appcommunicationcompany.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import uz.pdp.appcommunicationcompany.model.dto.create.PackageDto;
import uz.pdp.appcommunicationcompany.model.resp.ApiResponse;
import uz.pdp.appcommunicationcompany.service.PackageService;

import javax.validation.Valid;

import static uz.pdp.appcommunicationcompany.controller.ApiController.API_PATH;

@RestController
@RequestMapping(API_PATH + "/package")
@RequiredArgsConstructor
public class PackageController {
    private final PackageService packageService;

    @GetMapping
    public ResponseEntity<?> getPackages() {
        ApiResponse apiResponse = packageService.getPackages();
        return ResponseEntity.status(apiResponse.isStatus() ? 200 : 400).body(apiResponse);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getPackage(@PathVariable Long id) {
        ApiResponse apiResponse = packageService.getPackage(id);
        return ResponseEntity.status(apiResponse.isStatus() ? 200 : 400).body(apiResponse);
    }

    @PostMapping
    @PreAuthorize("hasRole('NUMBER_MANAGER')")
    public ResponseEntity<?> createPackage(@Valid @RequestBody PackageDto dto) {
        ApiResponse apiResponse = packageService.createPackage(dto);
        return ResponseEntity.status(apiResponse.isStatus() ? 201 : 400).body(apiResponse);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('NUMBER_MANAGER')")
    public ResponseEntity<?> editPackage(@PathVariable Long id, @Valid @RequestBody PackageDto dto) {
        ApiResponse apiResponse = packageService.editPackage(id,dto);
        return ResponseEntity.status(apiResponse.isStatus() ? 200 : 400).body(apiResponse);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('NUMBER_MANAGER')")
    public ResponseEntity<?> deletePackage(@PathVariable Long id) {
        ApiResponse apiResponse = packageService.deletePackage(id);
        return ResponseEntity.status(apiResponse.isStatus() ? 200 : 400).body(apiResponse);
    }

    @GetMapping("/report/top")
    @PreAuthorize("hasAnyRole('DIRECTOR, NUMBER_MANAGER')")
    public ResponseEntity<?> getReport(){
        ApiResponse apiResponse = packageService.getReport();
        return ResponseEntity.status(apiResponse.isStatus() ? 200 : 400).body(apiResponse);
    }
}