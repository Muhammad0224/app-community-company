package uz.pdp.appcommunicationcompany;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class AppCommunicationCompanyApplication {
    public static void main(String[] args) {
        SpringApplication.run(AppCommunicationCompanyApplication.class, args);
    }

//    @Bean
//    public CommandLineRunner run(EmployeeService employeeService, BranchService branchService) {
//        return args -> {
//            branchService.addBranch(new BranchDto("Main", BranchType.MAIN));
//
//            employeeService.addRole(new RoleDto("ROLE_DIRECTOR"));
//            employeeService.addRole(new RoleDto("ROLE_BRANCH_MANAGER"));
//            employeeService.addRole(new RoleDto("ROLE_HR_MANAGER"));
//            employeeService.addRole(new RoleDto("ROLE_BRANCH_DIRECTOR"));
//            employeeService.addRole(new RoleDto("ROLE_WORKER"));
//
//            employeeService.addEmployee(new EmployeeDto("Bahrom", "Akromov", "bahrom", "12345",1L));
//
//            employeeService.attachRole(new AttachRoleDto("bahrom", "ROLE_DIRECTOR"));
//        };
//    }
}
