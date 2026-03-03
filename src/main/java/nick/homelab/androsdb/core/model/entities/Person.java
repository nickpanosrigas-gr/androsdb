package nick.homelab.androsdb.core.model.entities;

import jakarta.persistence.*;
import lombok.*;
import nick.homelab.androsdb.core.model.enums.Gender;
import java.util.List;

@Entity
@Table(name = "PERSON")
@Data @NoArgsConstructor @AllArgsConstructor
public class Person {
    @Id
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "surname_id")
    private Surname surname;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "father_name")
    private String fatherName;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "father_id")
    private Person father;

    @Enumerated(EnumType.STRING)
    @Column(length = 10)
    private Gender gender;

    @Column(name = "birth_year")
    private Integer birthYear;

    @Column(name = "death_year")
    private Integer deathYear;

    private String occupation;

    @Column(columnDefinition = "TEXT")
    private String comments;

    @OneToMany(mappedBy = "person")
    private List<EventParticipation> eventParticipations;
}