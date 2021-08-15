package uz.pdp.appcommunicationcompany.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class TariffOpportunity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Tariff tariff;

    private boolean additional;     // true -> the amount of service in the tariff     false -> the price of service in the tariff

    @Column(nullable = false)
    private String key;

    @Column(nullable = false)
    private String value = "0";
}
