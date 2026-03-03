package nick.homelab.androsdb.core.model.entities;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "CHURCH")
@Data @NoArgsConstructor @AllArgsConstructor
public class Church {
    @Id
    private Integer id;

    private String name;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "location_id")
    private Location location;

    @Column(columnDefinition = "TEXT")
    private String notes;
}