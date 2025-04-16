package io.pow.backend.uom;

import java.util.Date;
import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(indexes = {
    @Index(name = "uom_idx_code", columnList = "code"),
    @Index(name = "uom_idx_token", columnList = "token"),
    @Index(name = "uom_idx_secrete_profile_id", columnList = "secreteProfileId")
})
public class UoM {

    public UoM(UUID uomId) {
        this.token = uomId;
    }
    public UoM() {
        super();
    }

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID token;
    @Column(unique = true, nullable = false)
    private String code;
    private String description;
    private UUID secreteProfileId;
    private Date createdOn;
}
