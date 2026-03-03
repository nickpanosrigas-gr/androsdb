package nick.homelab.androsdb.core.model.entities;

import jakarta.persistence.*;
import lombok.*;
import nick.homelab.androsdb.core.model.enums.EventRole;

@Entity
@Table(name = "EVENT_PARTICIPATION")
@Data @NoArgsConstructor @AllArgsConstructor
public class EventParticipation {
    @Id
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "event_id")
    private Event event;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "person_id")
    private Person person;

    @Enumerated(EnumType.STRING)
    @Column(length = 50)
    private EventRole role;
}