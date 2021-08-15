package uz.pdp.appcommunicationcompany.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import uz.pdp.appcommunicationcompany.model.dto.create.USSDCodeDto;
import uz.pdp.appcommunicationcompany.model.resp.ApiResponse;
import uz.pdp.appcommunicationcompany.service.USSDService;

import javax.validation.Valid;

import static uz.pdp.appcommunicationcompany.controller.ApiController.API_PATH;

@RestController
@RequestMapping(API_PATH + "/ussd")
@RequiredArgsConstructor
public class USSDController {

    private final USSDService ussdService;

    @GetMapping
    @PreAuthorize("hasAnyRole('WORKER', 'USER','NUMBER_MANAGER')")
    public ResponseEntity<?> getCodes() {
        ApiResponse apiResponse = ussdService.getCodes();
        return ResponseEntity.status(apiResponse.isStatus() ? 200 : 400).body(apiResponse);
    }

    @GetMapping("{code}")
    @PreAuthorize("hasAnyRole('WORKER', 'USER','NUMBER_MANAGER')")
    public ResponseEntity<?> getCode(@PathVariable String code) {
        ApiResponse apiResponse = ussdService.getCode(code);
        return ResponseEntity.status(apiResponse.isStatus() ? 200 : 400).body(apiResponse);
    }

    @PostMapping
    @PreAuthorize("hasRole('NUMBER_MANAGER')")
    public ResponseEntity<?> createCode(@Valid @RequestBody USSDCodeDto dto) {
        ApiResponse apiResponse = ussdService.createCode(dto);
        return ResponseEntity.status(apiResponse.isStatus() ? 200 : 400).body(apiResponse);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('NUMBER_MANAGER')")
    public ResponseEntity<?> editCode(@PathVariable Long id, @Valid @RequestBody USSDCodeDto dto) {
        ApiResponse apiResponse = ussdService.editCode(dto, id);
        return ResponseEntity.status(apiResponse.isStatus() ? 200 : 400).body(apiResponse);
    }

    @DeleteMapping("/deActivate/{code}")
    @PreAuthorize("hasRole('NUMBER_MANAGER')")
    public ResponseEntity<?> deActivate(@PathVariable String code) {
        ApiResponse apiResponse = ussdService.deActivate(code);
        return ResponseEntity.status(apiResponse.isStatus() ? 200 : 400).body(apiResponse);
    }
}
