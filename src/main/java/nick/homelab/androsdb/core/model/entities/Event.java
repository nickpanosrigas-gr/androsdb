package nick.homelab.androsdb.core.model.entities;

import jakarta.persistence.*;
import lombok.*;
import nick.homelab.androsdb.core.model.enums.EventType;
import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "EVENT")
@Data @NoArgsConstructor @AllArgsConstructor
public class Event {
    @Id
    private Integer id;

    @Enumerated(EnumType.STRING)
    @Column(name = "event_type", length = 50)
    private EventType eventType;

    @Column(name = "event_date")
    private LocalDate eventDate;

    @Column(name = "year_only")
    private Integer yearOnly;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "location_id")
    private Location location;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "church_id")
    private Church church;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "source_id")
    private Source source;

    @Column(name = "source_detail")
    private String sourceDetail;

    @OneToMany(mappedBy = "event")
    private List<EventParticipation> eventParticipations;
}