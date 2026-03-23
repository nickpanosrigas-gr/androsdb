package nick.homelab.androsdb.core.model.entities;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.proxy.HibernateProxy;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "SOURCE")
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Source extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false, length = 500)
    private String title;

    private String author;

    @Column(name = "archive_name")
    private String archiveName;

    @Column(columnDefinition = "TEXT")
    private String notes;

    @OneToMany(mappedBy = "source")
    @ToString.Exclude
    private List<Event> events;

    @OneToMany(mappedBy = "source")
    @ToString.Exclude
    private List<SurnameHistory> surnameHistories;

    @Override
    public final boolean equals(Object o) {
        if (this == o) return true;
        if (o == null) return false;
        Class<?> oEffectiveClass = o instanceof HibernateProxy ? ((HibernateProxy) o).getHibernateLazyInitializer().getPersistentClass() : o.getClass();
        Class<?> thisEffectiveClass = this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass() : this.getClass();
        if (thisEffectiveClass != oEffectiveClass) return false;
        Source source = (Source) o;
        return getId() != null && Objects.equals(getId(), source.getId());
    }

    @Override
    public final int hashCode() {
        return this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass().hashCode() : getClass().hashCode();
    }
}