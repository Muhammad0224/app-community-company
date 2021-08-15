package uz.pdp.appcommunicationcompany.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import uz.pdp.appcommunicationcompany.domain.Package;
import uz.pdp.appcommunicationcompany.model.resp.parser.PackageReport;

import java.util.List;
import java.util.Optional;

public interface PackageRepo extends JpaRepository<Package, Long> {
    Optional<Package> findByIdAndStatusTrue(Long id);

    boolean existsByName(String name);

    boolean existsByNameAndIdNot(String name, Long id);

    @Query(nativeQuery = true, value = "select count(*) as subscribers, t.name as package\n" +
            "from package t join subscriber_package sp on t.id = sp.a_package_id\n" +
            "group by t.name\n" +
            "order by count(*) desc")
    List<PackageReport> getReport();
}
