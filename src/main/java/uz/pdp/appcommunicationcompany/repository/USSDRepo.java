package uz.pdp.appcommunicationcompany.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.pdp.appcommunicationcompany.domain.USSDCode;

import java.util.List;
import java.util.Optional;

public interface USSDRepo extends JpaRepository<USSDCode, Long> {
    List<USSDCode> findAllByStatusTrue();

    Optional<USSDCode> findByCodeAndStatusTrue(String code);

    boolean existsByCode(String code);

    boolean existsByCodeAndIdNot(String code, Long id);

    Optional<USSDCode> findByIdAndStatusTrue(Long id);
}
