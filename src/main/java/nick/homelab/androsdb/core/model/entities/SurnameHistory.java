package nick.homelab.androsdb.core.model.entities;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;
import nick.homelab.androsdb.core.model.enums.SurnameHistoryType;
import org.hibernate.proxy.HibernateProxy;
import java.util.Map;
import java.util.Objects;

@Entity
@Table(name = "SURNAME_HISTORY")
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class SurnameHistory extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "surname_id")
    @ToString.Exclude
    private Surname surname;

    @Column(name = "year")
    private Integer year;

    @Enumerated(EnumType.STRING)
    @Column(name = "event_type", length = 50)
    private SurnameHistoryType eventType;

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

    @Column(name = "registry_number")
    private String registryNumber;

    @Column(name = "ancestor_name")
    private String ancestorName;

    @Column(name = "assets_text", columnDefinition = "TEXT")
    private String assetsText;

    // Hibernate 6 JSON mapping
    @JdbcTypeCode(SqlTypes.JSON)
    @Column(name = "assets_data", columnDefinition = "jsonb")
    private Map<String, Object> assetsData;

    @Column(name = "source_detail")
    private String sourceDetail;

    @Column(columnDefinition = "TEXT")
    private String notes;

    @Override
    public final boolean equals(Object o) {
        if (this == o) return true;
        if (o == null) return false;
        Class<?> oEffectiveClass = o instanceof HibernateProxy ? ((HibernateProxy) o).getHibernateLazyInitializer().getPersistentClass() : o.getClass();
        Class<?> thisEffectiveClass = this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass() : this.getClass();
        if (thisEffectiveClass != oEffectiveClass) return false;
        SurnameHistory that = (SurnameHistory) o;
        return getId() != null && Objects.equals(getId(), that.getId());
    }

    @Override
    public final int hashCode() {
        return this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass().hashCode() : getClass().hashCode();
    }
}