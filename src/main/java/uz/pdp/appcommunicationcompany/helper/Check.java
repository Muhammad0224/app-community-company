package uz.pdp.appcommunicationcompany.helper;

import org.springframework.stereotype.Component;
import uz.pdp.appcommunicationcompany.domain.Employee;
import uz.pdp.appcommunicationcompany.domain.Role;
import uz.pdp.appcommunicationcompany.enums.BranchType;
import uz.pdp.appcommunicationcompany.enums.UserRole;

@Component
public class Check {
    public static boolean hasRole(Employee employee, UserRole userRole) {
        for (Role role : employee.getRoles()) {
            if (role.getName().equals(userRole.getCode()))
                return true;
        }
        return false;
    }

    public static boolean accessRole(String from, String to, BranchType fromBranch, BranchType toBranch) {
        if (fromBranch == BranchType.MAIN && toBranch == BranchType.MAIN) {
            return (from.equals(UserRole.DIRECTOR.getCode()) || to.equals(UserRole.WORKER.getCode()));
        } else if (fromBranch == BranchType.MAIN && toBranch == BranchType.FILIAL) {
            return (from.equals(UserRole.DIRECTOR.getCode()) || from.equals(UserRole.BRANCH_MANAGER.getCode()));
        } else if (fromBranch == BranchType.FILIAL && toBranch == BranchType.FILIAL) {
            return (from.equals(UserRole.BRANCH_DIRECTOR.getCode()) || to.equals(UserRole.WORKER.getCode()));
        }
        return false;
    }

    public static boolean access(Employee from, Employee to) {
        if (from.getBranch().getBranchType() == BranchType.FILIAL
                && from.getBranch().getId().equals(to.getBranch().getId()) || from.getBranch().getBranchType() == BranchType.MAIN) {
            if (hasRole(to, UserRole.DIRECTOR))
                return false;
            for (Role fromRole : from.getRoles()) {
                for (Role toRole : to.getRoles()) {
                    return (accessRole(fromRole.getName(), toRole.getName(), from.getBranch().getBranchType(), to.getBranch().getBranchType()));
                }
            }
        }
        return false;
    }
}
