package uz.pdp.appcommunicationcompany.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import uz.pdp.appcommunicationcompany.domain.Client;
import uz.pdp.appcommunicationcompany.domain.Employee;
import uz.pdp.appcommunicationcompany.repository.ClientRepo;
import uz.pdp.appcommunicationcompany.repository.EmployeeRepo;

import java.util.Collection;
import java.util.HashSet;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserDetailsService {

    private final EmployeeRepo employeeRepo;

    private final ClientRepo clientRepo;

    @Override
    public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {
        if (employeeRepo.existsByLoginAndStatusTrue(login)) {
            Optional<Employee> optionalEmployee = employeeRepo.findByLoginAndStatusTrue(login);
            if (!optionalEmployee.isPresent())
                throw new UsernameNotFoundException("User not found");
            Employee employee = optionalEmployee.get();
            Collection<GrantedAuthority> authorities = new HashSet<>();
            employee.getRoles().forEach(role -> authorities.add(new SimpleGrantedAuthority(role.getName())));
            return new User(employee.getLogin(), employee.getPassword(), authorities);
        }

        Optional<Client> optionalClient = clientRepo.findByLogin(login);
        if (!optionalClient.isPresent())
            throw new UsernameNotFoundException("User not found");
        Client client = optionalClient.get();
        Collection<GrantedAuthority> authorities = new HashSet<>();
        authorities.add(new SimpleGrantedAuthority(client.getRole().getName()));
        return new User(client.getLogin(), client.getPassword(), authorities);
    }
}
