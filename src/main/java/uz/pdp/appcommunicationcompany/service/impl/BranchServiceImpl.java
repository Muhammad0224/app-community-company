package uz.pdp.appcommunicationcompany.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;
import uz.pdp.appcommunicationcompany.domain.Branch;
import uz.pdp.appcommunicationcompany.domain.Employee;
import uz.pdp.appcommunicationcompany.enums.BranchType;
import uz.pdp.appcommunicationcompany.enums.UserRole;
import uz.pdp.appcommunicationcompany.mapper.MapstructMapper;
import uz.pdp.appcommunicationcompany.model.dto.create.BranchDto;
import uz.pdp.appcommunicationcompany.model.dto.edit.BranchEditDto;
import uz.pdp.appcommunicationcompany.model.resp.ApiResponse;
import uz.pdp.appcommunicationcompany.repository.BranchRepo;
import uz.pdp.appcommunicationcompany.repository.EmployeeRepo;
import uz.pdp.appcommunicationcompany.service.BranchService;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

import static uz.pdp.appcommunicationcompany.helper.Check.hasRole;

@Service
@Transactional
@RequiredArgsConstructor
public class BranchServiceImpl implements BranchService {

    private final BranchRepo branchRepo;

    private final EmployeeServiceImpl employeeService;

    private final EmployeeRepo employeeRepo;

    private final MapstructMapper mapper;

    @Override
    public ApiResponse getBranches() {
        List<Branch> branches = branchRepo.findAllByStatusTrue();
        return new ApiResponse("OK", true, mapper.toBranchDto(branches));
    }

    @Override
    public ApiResponse getBranch(Long id) {
        Optional<Branch> optionalBranch = branchRepo.findByIdAndStatusTrue(id);
        if (optionalBranch.isPresent()) {
            Branch branch = optionalBranch.get();
            if (branch.getManager().getLogin().equals(getCurrentUser().getLogin()) || hasRole(getCurrentUser(), UserRole.DIRECTOR))
                return new ApiResponse("OK", true, mapper.toBranchDto(branch));
            return new ApiResponse("Yo don't have access", false);
        }
        return new ApiResponse("Branch not found", false);
    }

    @Override
    public ApiResponse addBranch(BranchDto dto) {
        if (branchRepo.existsByName(dto.getName()))
            return new ApiResponse("Conflict", false);
        Branch branch = new Branch();
        if (dto.getBranchType() == null) {
            branch.setBranchType(BranchType.FILIAL);
        } else branch.setBranchType(BranchType.MAIN);
        branch.setName(dto.getName());
        Optional<Employee> optionalEmployee = employeeRepo.findByLoginAndStatusTrue(dto.getManagerLogin());
        if (!optionalEmployee.isPresent())
            return new ApiResponse("Employee not found", false);
        Employee manager = optionalEmployee.get();
        if (!hasRole(manager,UserRole.BRANCH_MANAGER) || manager.getBranch().getBranchType()!=BranchType.MAIN)
            return new ApiResponse("Selected employee has not access to rule branch", false);
        branch.setManager(manager);
        return new ApiResponse("Created", true, branchRepo.save(branch));
    }

    @Override
    public ApiResponse deleteBranch(Long id) {
        Optional<Branch> optionalBranch = branchRepo.findByIdAndStatusTrue(id);
        if (!optionalBranch.isPresent())
            return new ApiResponse("Branch not found", false);
        Branch branch = optionalBranch.get();
        branch.setStatus(false);
        branchRepo.save(branch);
        return new ApiResponse("Deleted", true);
    }

    @Override
    public ApiResponse getBranchEmployees(Long id) {
        if (getCurrentUser().getBranch().getBranchType() == BranchType.FILIAL) {
            if (!getCurrentUser().getBranch().getId().equals(id))
                return new ApiResponse("You don't have access", false);
        }
        if (branchRepo.findByIdAndStatusTrue(id).isPresent())
            return new ApiResponse("OK", true, mapper.toEmployeeDto(employeeService.getBranchEmployee(id)));
        return new ApiResponse("Branch not found", false);
    }

    public Employee getCurrentUser() {
        return employeeRepo.findByLoginAndStatusTrue(((User) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername()).get();
    }

    @Override
    public ApiResponse editBranch(BranchEditDto dto, Long id) {
        if (branchRepo.existsByNameAndIdNot(dto.getName(), id))
            return new ApiResponse("This branch has already existed", false);
        Optional<Branch> optionalBranch = branchRepo.findByIdAndStatusTrue(id);
        if (!optionalBranch.isPresent())
            return new ApiResponse("Branch not found", false);
        Optional<Employee> optionalEmployee = employeeRepo.findByLoginAndStatusTrue(dto.getManagerLogin());
        if (!optionalEmployee.isPresent())
            return new ApiResponse("Employee not found", false);
        Employee manager = optionalEmployee.get();
        if (!hasRole(manager,UserRole.BRANCH_MANAGER) || manager.getBranch().getBranchType()!=BranchType.MAIN)
            return new ApiResponse("Selected employee has not access to rule branch", false);

        Branch branch = optionalBranch.get();
        branch.setName(dto.getName());
        branch.setManager(manager);
        return new ApiResponse("Updated", true, branchRepo.save(branch));
    }
}
