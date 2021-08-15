package uz.pdp.appcommunicationcompany.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class SimCard {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    private Number number;

    @ManyToOne
    private Client client;

    @ManyToOne
    private Branch branch;

    private Double balance = 0D;

    private Double price;

    private LocalDate createdAt = LocalDate.now();

    private boolean status = true;
}
