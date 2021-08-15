package uz.pdp.appcommunicationcompany.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import uz.pdp.appcommunicationcompany.enums.ClientType;
import uz.pdp.appcommunicationcompany.enums.UserRole;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(uniqueConstraints = @UniqueConstraint(columnNames = {"passportSeries", "passportNumber"}))
public class Client {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String firstname;

    @Column
    private String lastname;

    @Column(nullable = false, unique = true)
    private String login;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String passportSeries;

    @Column(nullable = false)
    private String passportNumber;

    @Column
    @Enumerated(value = EnumType.STRING)
    private ClientType clientType;

    @ManyToOne
    private Role role;

    @OneToMany(mappedBy = "client", cascade = CascadeType.PERSIST)
    private Set<SimCard> simCards = new HashSet<>();

    @Transient
    private String passport;

    public String getPassport(String passport) {
        return this.passportSeries + this.passportNumber;
    }
}
