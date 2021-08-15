package uz.pdp.appcommunicationcompany.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.pdp.appcommunicationcompany.domain.ServiceCategory;
import uz.pdp.appcommunicationcompany.enums.ServiceCategoryEnum;

import java.util.Optional;

public interface ServiceCategoryRepo extends JpaRepository<ServiceCategory, Long> {
    Optional<ServiceCategory> findByName(ServiceCategoryEnum name);
}
