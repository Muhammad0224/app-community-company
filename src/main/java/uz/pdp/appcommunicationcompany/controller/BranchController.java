package uz.pdp.appcommunicationcompany.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import uz.pdp.appcommunicationcompany.model.dto.create.BranchDto;
import uz.pdp.appcommunicationcompany.model.dto.edit.BranchEditDto;
import uz.pdp.appcommunicationcompany.model.resp.ApiResponse;
import uz.pdp.appcommunicationcompany.service.BranchService;

import javax.validation.Valid;

import static uz.pdp.appcommunicationcompany.controller.ApiController.API_PATH;

@RestController
@RequestMapping(API_PATH + "/branch")
@RequiredArgsConstructor
public class BranchController {
   private final BranchService branchService;

    @GetMapping
    @PreAuthorize("hasRole('DIRECTOR')")
    public ResponseEntity<?> getBranches(){
        ApiResponse apiResponse = branchService.getBranches();
        return ResponseEntity.status(apiResponse.isStatus() ? 200 : 409).body(apiResponse);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('DIRECTOR', 'BRANCH_MANAGER')")
    public ResponseEntity<?> getBranch(@PathVariable Long id){
        ApiResponse apiResponse = branchService.getBranch(id);
        return ResponseEntity.status(apiResponse.isStatus() ? 200 : 409).body(apiResponse);
    }

    @GetMapping("/employees/{id}")
    @PreAuthorize("hasAnyRole('DIRECTOR', 'BRANCH_MANAGER', 'BRANCH_DIRECTOR')")
    public ResponseEntity<?> getBranchEmployees(@PathVariable Long id){
        ApiResponse apiResponse = branchService.getBranchEmployees(id);
        return ResponseEntity.status(apiResponse.isStatus() ? 200 : 409).body(apiResponse);
    }

    @PostMapping
    @PreAuthorize("hasRole('DIRECTOR')")
    public ResponseEntity<?> addBranch(@Valid @RequestBody BranchDto dto){
        ApiResponse apiResponse = branchService.addBranch(dto);
        return ResponseEntity.status(apiResponse.isStatus() ? 201 : 400).body(apiResponse);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('DIRECTOR')")
    public ResponseEntity<?> editBranch(@PathVariable Long id, @Valid @RequestBody BranchEditDto dto){
        ApiResponse apiResponse = branchService.editBranch(dto,id);
        return ResponseEntity.status(apiResponse.isStatus() ? 200 : 400).body(apiResponse);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('DIRECTOR')")
    public ResponseEntity<?> deleteBranch(@PathVariable Long id){
        ApiResponse apiResponse = branchService.deleteBranch(id);
        return ResponseEntity.status(apiResponse.isStatus() ? 200 : 404).body(apiResponse);
    }

}
