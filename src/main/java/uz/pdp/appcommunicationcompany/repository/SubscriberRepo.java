package uz.pdp.appcommunicationcompany.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.pdp.appcommunicationcompany.domain.Subscriber;

import java.util.Optional;

public interface SubscriberRepo extends JpaRepository<Subscriber, Long> {
    Optional<Subscriber> findBySimCard_Number_CodeAndSimCard_Number_Number(Short simCard_number_code, String simCard_number_number);
}
