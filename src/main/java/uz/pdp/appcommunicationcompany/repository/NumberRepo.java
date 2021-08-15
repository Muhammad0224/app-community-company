package uz.pdp.appcommunicationcompany.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.pdp.appcommunicationcompany.domain.Number;

import java.util.Optional;

public interface NumberRepo extends JpaRepository<Number, Long> {
    boolean existsByCodeAndNumber(Short code, String number);

    Optional<Number> findByCodeAndNumber(Short code, String number);
}
