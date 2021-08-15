package uz.pdp.appcommunicationcompany.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import uz.pdp.appcommunicationcompany.enums.BranchType;

import javax.persistence.*;

@Table(name = "branch")
@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Setter
public class Branch {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String name;

    @Enumerated(EnumType.STRING)
    private BranchType branchType;

    @ManyToOne
    private Employee manager;

    private boolean status = true;
}