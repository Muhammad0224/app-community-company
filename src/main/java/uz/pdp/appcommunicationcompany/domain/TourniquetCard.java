package uz.pdp.appcommunicationcompany.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.util.Date;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class TourniquetCard {
    @Id
    @GeneratedValue
    private UUID id;

    @ManyToOne
    private Branch branch;

    @ManyToOne
    private Employee employee;

    @CreationTimestamp
    @Column(updatable = false)
    private Date createdAt;

    @Column
    private boolean status = true;
}
