package uz.pdp.appcommunicationcompany.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Package {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String name;

    @Column(nullable = false)
    private String key;

    @Column(nullable = false)
    private Long value;

    @Column(nullable = false)
    private double price;

    @Column(nullable = false)
    private Integer validityDay;

    private boolean addResidue = false;

    @ManyToMany
    private List<Tariff> tariffs = new ArrayList<>();

    @OneToMany(mappedBy = "aPackage")
    private List<SubscriberPackage> subscriberPackages = new ArrayList<>();

    private boolean status = true;
}
