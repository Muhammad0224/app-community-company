package uz.pdp.appcommunicationcompany.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.pdp.appcommunicationcompany.domain.Payment;

public interface PaymentRepo extends JpaRepository<Payment, Long> {
}
