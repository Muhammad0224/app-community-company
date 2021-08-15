package uz.pdp.appcommunicationcompany.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.pdp.appcommunicationcompany.domain.ActivityType;

import java.util.Optional;

public interface ActivityTypeRepo extends JpaRepository<ActivityType, Long> {
    Optional<ActivityType> findByName(String name);
}
