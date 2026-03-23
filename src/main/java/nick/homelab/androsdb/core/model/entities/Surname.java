package nick.homelab.androsdb.core.model.entities;

import jakarta.persistence.*;
import lombok.*;
import nick.homelab.androsdb.core.model.enums.OriginCategory;
import org.hibernate.proxy.HibernateProxy;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "SURNAME")
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Surname extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String name;

    @Column(name = "latin_name")
    private String latinName;

    @Enumerated(EnumType.STRING)
    @Column(name = "origin_category", length = 100)
    private OriginCategory originCategory;

    @OneToMany(mappedBy = "surname")
    @ToString.Exclude
    private List<SurnameHistory> surnameHistories;

    @OneToMany(mappedBy = "surname")
    @ToString.Exclude
    private List<Person> persons;

    @Override
    public final boolean equals(Object o) {
        if (this == o) return true;
        if (o == null) return false;
        Class<?> oEffectiveClass = o instanceof HibernateProxy ? ((HibernateProxy) o).getHibernateLazyInitializer().getPersistentClass() : o.getClass();
        Class<?> thisEffectiveClass = this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass() : this.getClass();
        if (thisEffectiveClass != oEffectiveClass) return false;
        Surname surname = (Surname) o;
        return getId() != null && Objects.equals(getId(), surname.getId());
    }

    @Override
    public final int hashCode() {
        return this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass().hashCode() : getClass().hashCode();
    }
}