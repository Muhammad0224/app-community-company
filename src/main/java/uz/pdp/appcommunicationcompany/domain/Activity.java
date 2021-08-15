package uz.pdp.appcommunicationcompany.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Activity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Subscriber subscriber;

    @ManyToOne
    private ActivityType activityType;

    @Column(length = 3000)
    private String details;

    @Column
    private LocalDateTime createdAt = LocalDateTime.now();
}
