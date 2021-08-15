package uz.pdp.appcommunicationcompany.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.pdp.appcommunicationcompany.domain.Activity;

import java.time.LocalDateTime;
import java.util.List;

public interface ActivityRepo extends JpaRepository<Activity, Long> {
    List<Activity> findAllBySubscriberIdAndCreatedAtBetween(Long subscriber_id, LocalDateTime createdAt, LocalDateTime createdAt2);
}
