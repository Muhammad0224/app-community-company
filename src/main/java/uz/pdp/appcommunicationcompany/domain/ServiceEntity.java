package uz.pdp.appcommunicationcompany.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import uz.pdp.appcommunicationcompany.enums.TariffType;

import javax.persistence.*;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "service")
public class ServiceEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false,unique = true)
    private String name;

    @OneToOne
    private ServiceCategory serviceCategory;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private TariffType type;

    private double price;

    private boolean status = true;
}
