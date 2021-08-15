package uz.pdp.appcommunicationcompany.domain;

import com.google.gson.Gson;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Subscriber {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Tariff tariff;

    @OneToOne
    private SimCard simCard;

    private LocalDate activationDate;

    private LocalDate expirationDate;

    private LocalDate creationDate = LocalDate.now();

    private boolean serviceDebt;

    private boolean status = true;

    @OneToMany(mappedBy = "subscriber", cascade = CascadeType.PERSIST)
    private List<ResidueOpportunity> opportunities = new ArrayList<>();

    @ManyToMany
    private List<ServiceEntity> services = new ArrayList<>();

    @OneToMany(mappedBy = "subscriber", cascade = CascadeType.PERSIST)
    private List<SubscriberPackage> subscriberPackages = new ArrayList<>();
}
