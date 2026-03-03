package nick.homelab.androsdb.core.model.entities;

import jakarta.persistence.*;
import lombok.*;
import nick.homelab.androsdb.core.model.enums.OriginCategory;
import java.util.List;

@Entity
@Table(name = "SURNAME")
@Data @NoArgsConstructor @AllArgsConstructor
public class Surname {
    @Id
    private Integer id;

    private String name;

    @Column(name = "latin_name")
    private String latinName;

    @Enumerated(EnumType.STRING)
    @Column(name = "origin_category", length = 100)
    private OriginCategory originCategory;

    @OneToMany(mappedBy = "surname")
    private List<SurnameHistory> surnameHistories;

    @OneToMany(mappedBy = "surname")
    private List<Person> persons;
}