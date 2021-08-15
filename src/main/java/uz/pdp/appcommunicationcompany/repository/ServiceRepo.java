package uz.pdp.appcommunicationcompany.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import uz.pdp.appcommunicationcompany.domain.ServiceEntity;

import java.util.List;
import java.util.Optional;

public interface ServiceRepo extends JpaRepository<ServiceEntity, Long> {
    Optional<ServiceEntity> findByIdAndStatusTrue(Long aLong);

    boolean existsByName(String name);

    boolean existsByNameAndIdNot(String name, Long id);

    @Query(nativeQuery = true, value = "select t.name\n" +
            "from service t\n" +
            "         join subscriber_services ss on t.id = ss.services_id\n" +
            "group by t.name order by count(*) desc ")
    List<String> getTopServices();

    @Query(nativeQuery = true, value = "select t.name\n" +
            "from service t\n" +
            "         join subscriber_services ss on t.id = ss.services_id\n" +
            "group by t.name order by count(*)")
    List<String> getPoorServices();
}
