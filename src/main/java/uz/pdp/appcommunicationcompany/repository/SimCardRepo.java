package uz.pdp.appcommunicationcompany.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import uz.pdp.appcommunicationcompany.domain.Number;
import uz.pdp.appcommunicationcompany.domain.SimCard;
import uz.pdp.appcommunicationcompany.model.resp.parser.SimCardReportRespDto;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface SimCardRepo extends JpaRepository<SimCard, Long> {
    List<SimCard> findAllByStatusTrue();

    Optional<SimCard> findByIdAndStatusTrue(Long id);

    SimCard findByNumber(Number number);

    @Query(nativeQuery = true, value = "select count(*) as soldSimCard, b.name from sim_card t\n" +
            "join branch b on b.id = t.branch_id\n" +
            "group by b.name order by count(*) desc")
    List<SimCardReportRespDto> getReport();

    @Query(nativeQuery = true, value = "select sum(t.price)\n" +
            "from sim_card t\n" +
            "where t.created_at between :date1 and :date2")
    Double getIncome(LocalDate date1, LocalDate date2);
}
