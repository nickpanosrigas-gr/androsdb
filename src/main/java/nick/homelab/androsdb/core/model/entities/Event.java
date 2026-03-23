package nick.homelab.androsdb.core.model.entities;

import jakarta.persistence.*;
import lombok.*;
import nick.homelab.androsdb.core.model.enums.EventType;
import org.hibernate.proxy.HibernateProxy;
import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "EVENT")
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Event extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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
    @ToString.Exclude
    private Location location;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "church_id")
    @ToString.Exclude
    private Church church;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "source_id")
    @ToString.Exclude
    private Source source;

    @Column(name = "source_detail")
    private String sourceDetail;

    @OneToMany(mappedBy = "event")
    @ToString.Exclude
    private List<EventParticipation> eventParticipations;

    @Override
    public final boolean equals(Object o) {
        if (this == o) return true;
        if (o == null) return false;
        Class<?> oEffectiveClass = o instanceof HibernateProxy ? ((HibernateProxy) o).getHibernateLazyInitializer().getPersistentClass() : o.getClass();
        Class<?> thisEffectiveClass = this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass() : this.getClass();
        if (thisEffectiveClass != oEffectiveClass) return false;
        Event event = (Event) o;
        return getId() != null && Objects.equals(getId(), event.getId());
    }

    @Override
    public final int hashCode() {
        return this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass().hashCode() : getClass().hashCode();
    }
}