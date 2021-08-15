package uz.pdp.appcommunicationcompany.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uz.pdp.appcommunicationcompany.domain.Client;

import java.util.List;
import java.util.Optional;
@Repository

public interface ClientRepo extends JpaRepository<Client, Long> {
    Optional<Client> findByLogin(String login);

    boolean existsByLogin(String login);

    boolean existsByLoginAndIdNot(String login, Long id);

    Optional<Client> findByPassportSeriesAndPassportNumber(String passportSeries, String passportNumber);
}
