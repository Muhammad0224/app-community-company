package uz.pdp.appcommunicationcompany.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uz.pdp.appcommunicationcompany.domain.TourniquetCard;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface TourniquetRepo extends JpaRepository<TourniquetCard, UUID> {
    Optional<TourniquetCard> findByEmployee_LoginAndStatusTrue(String employee_login);
}
