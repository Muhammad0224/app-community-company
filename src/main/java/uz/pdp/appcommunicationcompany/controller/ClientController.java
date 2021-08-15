package uz.pdp.appcommunicationcompany.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import uz.pdp.appcommunicationcompany.model.dto.create.ClientDto;
import uz.pdp.appcommunicationcompany.model.dto.edit.ClientEditDto;
import uz.pdp.appcommunicationcompany.model.resp.ApiResponse;
import uz.pdp.appcommunicationcompany.service.ClientService;

import javax.validation.Valid;

import static uz.pdp.appcommunicationcompany.controller.ApiController.API_PATH;

@RestController
@RequestMapping(API_PATH + "/client")
@RequiredArgsConstructor
public class ClientController {
    private final ClientService clientService;

    @GetMapping
    @PreAuthorize("hasAnyRole('DIRECTOR', 'WORKER', 'BRANCH_MANAGER')")
    public ResponseEntity<?> getClients() {
        ApiResponse apiResponse = clientService.getClients();
        return ResponseEntity.status(apiResponse.isStatus() ? 200 : 409).body(apiResponse);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('DIRECTOR', 'WORKER', 'BRANCH_MANAGER')")
    public ResponseEntity<?> getClient(@PathVariable Long id) {
        ApiResponse apiResponse = clientService.getClient(id);
        return ResponseEntity.status(apiResponse.isStatus() ? 200 : 409).body(apiResponse);
    }

    @PostMapping
    @PreAuthorize("hasRole('WORKER')")
    public ResponseEntity<?> createClient(@Valid @RequestBody ClientDto dto) {
        ApiResponse apiResponse = clientService.createClient(dto);
        return ResponseEntity.status(apiResponse.isStatus() ? 200 : 400).body(apiResponse);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('WORKER', 'USER')")
    public ResponseEntity<?> updateClient(@PathVariable Long id, @Valid @RequestBody ClientEditDto dto) {
        ApiResponse apiResponse = clientService.updateClient(id, dto);
        return ResponseEntity.status(apiResponse.isStatus() ? 200 : 400).body(apiResponse);
    }
}
