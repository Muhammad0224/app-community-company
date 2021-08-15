package uz.pdp.appcommunicationcompany.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uz.pdp.appcommunicationcompany.domain.Employee;

import java.util.List;
import java.util.Optional;
@Repository

public interface EmployeeRepo extends JpaRepository<Employee, Long> {
    Optional<Employee> findByLoginAndStatusTrue(String login);

    Optional<Employee> findByIdAndStatusTrue(Long id);

    List<Employee> findAllByStatusTrue();

    List<Employee> findAllByStatusTrueAndBranchId(Long branch_id);

    boolean existsByLoginAndStatusTrue(String login);

    boolean existsByLogin(String login);

    boolean existsByLoginAndIdNot(String login, Long id);
}
