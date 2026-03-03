package nick.homelab.androsdb.core.model.entities;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;
import nick.homelab.androsdb.core.model.enums.SurnameHistoryType;
import java.util.Map;

@Entity
@Table(name = "SURNAME_HISTORY")
@Data @NoArgsConstructor @AllArgsConstructor
public class SurnameHistory {
    @Id
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "surname_id")
    private Surname surname;

    @Column(name = "year")
    private Integer year;

    @Enumerated(EnumType.STRING)
    @Column(name = "event_type", length = 50)
    private SurnameHistoryType eventType;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "location_id")
    private Location location;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "church_id")
    private Church church;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "source_id")
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
}