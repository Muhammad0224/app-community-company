package uz.pdp.appcommunicationcompany.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import uz.pdp.appcommunicationcompany.domain.Branch;
import uz.pdp.appcommunicationcompany.domain.Employee;
import uz.pdp.appcommunicationcompany.domain.Role;
import uz.pdp.appcommunicationcompany.enums.BranchType;
import uz.pdp.appcommunicationcompany.enums.UserRole;
import uz.pdp.appcommunicationcompany.helper.CurrentUser;
import uz.pdp.appcommunicationcompany.mapper.MapstructMapper;
import uz.pdp.appcommunicationcompany.model.dto.create.AttachRoleDto;
import uz.pdp.appcommunicationcompany.model.dto.create.EmployeeDto;
import uz.pdp.appcommunicationcompany.model.dto.create.RoleDto;
import uz.pdp.appcommunicationcompany.model.dto.edit.EmployeeEditDto;
import uz.pdp.appcommunicationcompany.model.resp.ApiResponse;
import uz.pdp.appcommunicationcompany.repository.BranchRepo;
import uz.pdp.appcommunicationcompany.repository.EmployeeRepo;
import uz.pdp.appcommunicationcompany.repository.RoleRepo;
import uz.pdp.appcommunicationcompany.service.EmployeeService;
import uz.pdp.appcommunicationcompany.helper.Check;

import javax.transaction.Transactional;
import java.util.*;

import static uz.pdp.appcommunicationcompany.helper.Check.access;

@Service
@Transactional
@RequiredArgsConstructor
public class EmployeeServiceImpl implements EmployeeService {

    private final EmployeeRepo employeeRepo;

    private final PasswordEncoder passwordEncoder;

    private final BranchRepo branchRepo;

    private final RoleRepo roleRepo;

    private final MapstructMapper mapper;

    private final CurrentUser currentUser;

    @Override
    public ApiResponse getEmployee(Long id) {
        Optional<Employee> optionalEmployee = employeeRepo.findByIdAndStatusTrue(id);
        if (optionalEmployee.isPresent()) {
            Employee employee = optionalEmployee.get();
            return new ApiResponse("OK", true, mapper.toEmployeeDto(employee));
        }
        return new ApiResponse("Employee not found", false);
    }

    @Override
    public ApiResponse getEmployees() {
        List<Employee> employees = employeeRepo.findAllByStatusTrue();
        return new ApiResponse("OK", true, mapper.toEmployeeDto(employees));
    }

    @Override
    public ApiResponse addEmployee(EmployeeDto dto) {
        if (employeeRepo.existsByLogin(dto.getLogin()))
            return new ApiResponse("This login has already existed",false);
        Optional<Branch> optionalBranch = branchRepo.findByIdAndStatusTrue(dto.getBranchId());
        if (!optionalBranch.isPresent())
            return new ApiResponse("Branch not found", false);
        Branch branch = optionalBranch.get();
        Employee employee = new Employee();

        /* ----------Comment qilish kerak----------- */
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Employee active = employeeRepo.findByLoginAndStatusTrue(user.getUsername()).get();
        if (active.getBranch().getBranchType() == BranchType.FILIAL
                && !branch.getId().equals(active.getBranch().getId()))
            return new ApiResponse("You don't have access", false);
        /* ----------------------------------*/

        employee.setFirstname(dto.getFirstname());
        employee.setLastname(dto.getLastname());
        employee.setLogin(dto.getLogin());
        employee.setPassword(passwordEncoder.encode(dto.getPassword()));
        employee.setBranch(branch);
        employeeRepo.save(employee);
        attachRole(new AttachRoleDto(dto.getLogin(), UserRole.WORKER.getCode()));
        return new ApiResponse("Created", true);
    }

    @Override
    public ApiResponse addRole(RoleDto dto) {
        Role role = new Role();
        role.setName(dto.getName());
        return new ApiResponse("Created", true, roleRepo.save(role));
    }

    @Override
    public ApiResponse attachRole(AttachRoleDto dto) {
        Optional<Employee> optionalEmployee = employeeRepo.findByLoginAndStatusTrue(dto.getLogin());
        if (!optionalEmployee.isPresent())
            return new ApiResponse("Employee not found", false);
        Optional<Role> optionalRole = roleRepo.findByName(dto.getRoleName());
        if (!optionalRole.isPresent())
            return new ApiResponse("Role not found", false);

        Role role = optionalRole.get();
        Employee employee = optionalEmployee.get();

        /* --------------Comment qilish kerak---------------  */
        Employee active = currentUser.getCurrentUser(); ;
        boolean access = false;
        if ((active.getBranch().getBranchType() == BranchType.FILIAL
                && active.getBranch().getId().equals(employee.getBranch().getId())) || active.getBranch().getBranchType() == BranchType.MAIN) {
            for (Role activeRole : active.getRoles()) {
                if (Check.accessRole(activeRole.getName(), role.getName(), active.getBranch().getBranchType(), employee.getBranch().getBranchType())) {
                    access = true;
                    break;
                }
            }
        }
        /* ---------------------------------------- */

        if (access) {
            employee.getRoles().add(role);
            employeeRepo.save(employee);
            return new ApiResponse("OK", true);
        }
        return new ApiResponse("You cannot attach director", false);
    }

    @Override
    public ApiResponse deleteEmployee(Long id) {
        Optional<Employee> optionalEmployee = employeeRepo.findByIdAndStatusTrue(id);
        if (!optionalEmployee.isPresent())
            return new ApiResponse("Employee not found", false);
        Employee employee = optionalEmployee.get();
       if (access(currentUser.getCurrentUser(), employee)){
           employee.setStatus(false);
           employeeRepo.save(employee);
           return new ApiResponse("Deleted", true);
       }
       return new ApiResponse("You don't have access", false);
    }

    public List<Employee> getBranchEmployee(Long id) {
        return employeeRepo.findAllByStatusTrueAndBranchId(id);
    }

    @Override
    public ApiResponse getEmployee(String login) {
        Optional<Employee> optionalEmployee = employeeRepo.findByLoginAndStatusTrue(login);
        if (optionalEmployee.isPresent()) {
            Employee employee = optionalEmployee.get();
            return new ApiResponse("OK", true, mapper.toEmployeeDto(employee));
        }
        return new ApiResponse("Employee not found", false);
    }

    @Override
    public ApiResponse editEmployee(EmployeeEditDto dto, Long id) {
        if (employeeRepo.existsByLoginAndIdNot(dto.getLogin(), id))
            return new ApiResponse("This login has already existed", false);
        Optional<Employee> optionalEmployee = employeeRepo.findByIdAndStatusTrue(id);
        if (!optionalEmployee.isPresent())
            return new ApiResponse("Employee not found", false);
        Optional<Branch> optionalBranch = branchRepo.findByIdAndStatusTrue(dto.getBranchId());
        if (!optionalBranch.isPresent())
            return new ApiResponse("Branch not found", false);

        Employee employee = optionalEmployee.get();
        if (!access(currentUser.getCurrentUser(), employee))
            return new ApiResponse("You don't have access", false);
        employee.setPassword(passwordEncoder.encode(dto.getPassword()));
        employee.setBranch(optionalBranch.get());
        employee.setLastname(dto.getLastname());
        employee.setFirstname(dto.getFirstname());
        employeeRepo.save(employee);
        return new ApiResponse("Updated", true);
    }
}
