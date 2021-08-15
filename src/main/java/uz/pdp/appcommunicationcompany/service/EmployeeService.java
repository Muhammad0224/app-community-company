package uz.pdp.appcommunicationcompany.service;

import uz.pdp.appcommunicationcompany.model.dto.create.AttachRoleDto;
import uz.pdp.appcommunicationcompany.model.dto.create.EmployeeDto;
import uz.pdp.appcommunicationcompany.model.dto.create.RoleDto;
import uz.pdp.appcommunicationcompany.model.dto.edit.EmployeeEditDto;
import uz.pdp.appcommunicationcompany.model.resp.ApiResponse;

public interface EmployeeService {
    ApiResponse getEmployee(Long id);

    ApiResponse getEmployees();

    ApiResponse addEmployee(EmployeeDto dto);

    ApiResponse addRole(RoleDto dto);

    ApiResponse attachRole(AttachRoleDto dto);

    ApiResponse deleteEmployee(Long id);

    ApiResponse getEmployee(String login);

    ApiResponse editEmployee(EmployeeEditDto dto, Long id);
}
