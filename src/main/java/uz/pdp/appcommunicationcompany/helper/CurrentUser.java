package uz.pdp.appcommunicationcompany.helper;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;
import uz.pdp.appcommunicationcompany.domain.Client;
import uz.pdp.appcommunicationcompany.domain.Employee;
import uz.pdp.appcommunicationcompany.repository.ClientRepo;
import uz.pdp.appcommunicationcompany.repository.EmployeeRepo;

@RequiredArgsConstructor
@Component
public class CurrentUser {

    private final EmployeeRepo employeeRepo;

    private final ClientRepo clientRepo;

    public Employee getCurrentUser() {
        return employeeRepo.findByLoginAndStatusTrue(((User) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername()).get();
    }

    public Client getCurrentClient() {
        return clientRepo.findByLogin(((User) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername()).get();
    }
}
