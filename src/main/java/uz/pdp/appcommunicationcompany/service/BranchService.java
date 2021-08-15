package uz.pdp.appcommunicationcompany.service;

import uz.pdp.appcommunicationcompany.model.dto.create.BranchDto;
import uz.pdp.appcommunicationcompany.model.dto.edit.BranchEditDto;
import uz.pdp.appcommunicationcompany.model.resp.ApiResponse;

public interface BranchService {
    ApiResponse getBranches();

    ApiResponse getBranch(Long id);

    ApiResponse addBranch(BranchDto dto);

    ApiResponse deleteBranch(Long id);

    ApiResponse getBranchEmployees(Long id);

    ApiResponse editBranch(BranchEditDto dto, Long id);
}
