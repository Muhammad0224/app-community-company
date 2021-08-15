package uz.pdp.appcommunicationcompany.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.pdp.appcommunicationcompany.domain.TourniquetHistory;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public interface TourniquetHistoryRepo extends JpaRepository<TourniquetHistory, UUID> {
    List<TourniquetHistory> findAllByExitedAtBetween(LocalDateTime exitedAt, LocalDateTime exitedAt2);
    List<TourniquetHistory> findAllByEnteredAtBetween(LocalDateTime enteredAt, LocalDateTime enteredAt2);
}
