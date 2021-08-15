package uz.pdp.appcommunicationcompany.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import uz.pdp.appcommunicationcompany.model.dto.create.TariffDto;
import uz.pdp.appcommunicationcompany.model.resp.ApiResponse;
import uz.pdp.appcommunicationcompany.service.TariffService;

import javax.validation.Valid;

import static uz.pdp.appcommunicationcompany.controller.ApiController.API_PATH;

@RestController
@RequestMapping(API_PATH + "/tariff")
@RequiredArgsConstructor
public class TariffController {
    private final TariffService tariffService;

    @GetMapping
    public ResponseEntity<?> getTariffs() {
        ApiResponse apiResponse = tariffService.getTariffs();
        return ResponseEntity.status(apiResponse.isStatus() ? 200 : 400).body(apiResponse);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getTariff(@PathVariable Long id) {
        ApiResponse apiResponse = tariffService.getTariff(id);
        return ResponseEntity.status(apiResponse.isStatus() ? 200 : 400).body(apiResponse);
    }

    @PostMapping
    @PreAuthorize("hasAnyRole('NUMBER_MANAGER')")
    public ResponseEntity<?> createTariff(@Valid @RequestBody TariffDto dto){
        ApiResponse apiResponse = tariffService.createTariff(dto);
        return ResponseEntity.status(apiResponse.isStatus() ? 201 : 400).body(apiResponse);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('NUMBER_MANAGER')")
    public ResponseEntity<?> editTariff(@PathVariable Long id, @Valid @RequestBody TariffDto dto){
        ApiResponse apiResponse = tariffService.editTariff(id, dto);
        return ResponseEntity.status(apiResponse.isStatus() ? 200 : 400).body(apiResponse);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyRole('NUMBER_MANAGER')")
    public ResponseEntity<?> deleteTariff(@PathVariable Long id){
        ApiResponse apiResponse = tariffService.deleteTariff(id);
        return ResponseEntity.status(apiResponse.isStatus() ? 200 : 400).body(apiResponse);
    }
    
    
    @GetMapping("/report/top")
    @PreAuthorize("hasAnyRole('DIRECTOR, NUMBER_MANAGER')")
    public ResponseEntity<?> getReport(){
        ApiResponse apiResponse = tariffService.getReport();
        return ResponseEntity.status(apiResponse.isStatus() ? 200 : 400).body(apiResponse);
    }
}
