package uz.pdp.appcommunicationcompany.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import uz.pdp.appcommunicationcompany.model.dto.create.AttachRoleDto;
import uz.pdp.appcommunicationcompany.model.dto.create.EmployeeDto;
import uz.pdp.appcommunicationcompany.model.dto.create.RoleDto;
import uz.pdp.appcommunicationcompany.model.dto.edit.EmployeeEditDto;
import uz.pdp.appcommunicationcompany.model.resp.ApiResponse;
import uz.pdp.appcommunicationcompany.service.EmployeeService;

import javax.validation.Valid;

import static uz.pdp.appcommunicationcompany.controller.ApiController.API_PATH;

@RestController
@RequestMapping(path = API_PATH + "/employee")
@RequiredArgsConstructor
public class EmployeeController {

    private final EmployeeService employeeService;

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('DIRECTOR','HR_MANAGER', 'BRANCH_MANAGER', 'BRANCH_DIRECTOR')")
    public ResponseEntity<?> getEmployee(@PathVariable Long id) {
        ApiResponse apiResponse = employeeService.getEmployee(id);
        return ResponseEntity.status(apiResponse.isStatus() ? 200 : 404).body(apiResponse);
    }

    @GetMapping("/{login}")
    @PreAuthorize("hasAnyRole('DIRECTOR','HR_MANAGER', 'BRANCH_MANAGER', 'BRANCH_DIRECTOR')")
    public ResponseEntity<?> getEmployee(@PathVariable String login) {
        ApiResponse apiResponse = employeeService.getEmployee(login);
        return ResponseEntity.status(apiResponse.isStatus() ? 200 : 404).body(apiResponse);
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('DIRECTOR','HR_MANAGER')")
    public ResponseEntity<?> getEmployees() {
        ApiResponse apiResponse = employeeService.getEmployees();
        return ResponseEntity.status(apiResponse.isStatus() ? 200 : 401).body(apiResponse);
    }

    @PostMapping
    @PreAuthorize("hasAnyRole('DIRECTOR', 'HR_MANAGER', 'BRANCH_MANAGER', 'BRANCH_DIRECTOR')")
    public ResponseEntity<?> addEmployee(@Valid @RequestBody EmployeeDto dto) {
        ApiResponse apiResponse = employeeService.addEmployee(dto);
        return ResponseEntity.status(apiResponse.isStatus() ? 201 : 400).body(apiResponse);
    }

    @PostMapping("/role")
    @PreAuthorize("hasRole('DIRECTOR')")
    public ResponseEntity<?> addRole(@Valid @RequestBody RoleDto dto) {
        ApiResponse apiResponse = employeeService.addRole(dto);
        return ResponseEntity.status(apiResponse.isStatus() ? 201 : 400).body(apiResponse);
    }

    @PostMapping("/attach")
    @PreAuthorize("hasAnyRole('DIRECTOR', 'BRANCH_MANAGER', 'BRANCH_DIRECTOR')")
    public ResponseEntity<?> attachRole(@Valid @RequestBody AttachRoleDto dto) {
        ApiResponse apiResponse = employeeService.attachRole(dto);
        return ResponseEntity.status(apiResponse.isStatus() ? 200 : 400).body(apiResponse);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('DIRECTOR', 'HR_MANAGER', 'BRANCH_MANAGER', 'BRANCH_DIRECTOR')")
    public ResponseEntity<?> editEmployee(@Valid @RequestBody EmployeeEditDto dto, @PathVariable Long id){
        ApiResponse apiResponse = employeeService.editEmployee(dto, id);
        return ResponseEntity.status(apiResponse.isStatus() ? 200 : 400).body(apiResponse);
    }

    @DeleteMapping("/delete/{id}")
    @PreAuthorize("hasAnyRole('DIRECTOR', 'BRANCH_MANAGER', 'BRANCH_DIRECTOR')")
    public ResponseEntity<?> deleteEmployee(@PathVariable Long id) {
        ApiResponse apiResponse = employeeService.deleteEmployee(id);
        return ResponseEntity.status(apiResponse.isStatus() ? 200 : 404).body(apiResponse);
    }
}
