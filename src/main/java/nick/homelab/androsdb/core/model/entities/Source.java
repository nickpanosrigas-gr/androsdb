package nick.homelab.androsdb.core.model.entities;

import jakarta.persistence.*;
import lombok.*;
import java.util.List;

@Entity
@Table(name = "SOURCE")
@Data @NoArgsConstructor @AllArgsConstructor
public class Source {
    @Id
    private Integer id;

    @Column(nullable = false, length = 500)
    private String title;

    private String author;

    @Column(name = "archive_name")
    private String archiveName;

    @Column(columnDefinition = "TEXT")
    private String notes;

    @OneToMany(mappedBy = "source")
    private List<Event> events;

    @OneToMany(mappedBy = "source")
    private List<SurnameHistory> surnameHistories;
}