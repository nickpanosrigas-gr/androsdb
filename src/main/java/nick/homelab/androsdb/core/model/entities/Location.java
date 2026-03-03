package nick.homelab.androsdb.core.model.entities;

import jakarta.persistence.*;
import lombok.*;
import java.math.BigDecimal;
import java.util.List;

@Entity
@Table(name = "LOCATION")
@Data @NoArgsConstructor @AllArgsConstructor
public class Location {
    @Id
    private Integer id;

    private String name;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_location_id")
    private Location parentLocation;

    @Column(length = 50)
    private String type; // e.g., 'ISLAND', 'VILLAGE', 'PARISH'

    @Column(precision = 9, scale = 6)
    private BigDecimal latitude;

    @Column(precision = 9, scale = 6)
    private BigDecimal longitude;

    @OneToMany(mappedBy = "location")
    private List<Church> churches;

    @OneToMany(mappedBy = "location")
    private List<Event> events;

    @OneToMany(mappedBy = "location")
    private List<SurnameHistory> surnameHistories;
}