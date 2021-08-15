package uz.pdp.appcommunicationcompany.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import uz.pdp.appcommunicationcompany.enums.ClientType;
import uz.pdp.appcommunicationcompany.enums.TariffType;

import javax.persistence.*;
import java.util.List;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Tariff {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String name;

    @Enumerated(EnumType.STRING)
    private TariffType tariffType;

    @Column(nullable = false)
    private Double price;

    @Column
    @Enumerated(value = EnumType.STRING)
    private ClientType clientType;

    @Column(nullable = false)
    private Double connectPrice;

    @Column
    private String description;

    @OneToMany(mappedBy = "tariff", cascade = CascadeType.PERSIST)
    private List<TariffOpportunity> tariffOpportunities;

    private boolean status = true;
}
