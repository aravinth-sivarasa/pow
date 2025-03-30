package io.pow.backend.uom;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import lombok.Data;

@Data
@Entity
public class UOM {

    public UOM(Long uomId) {
        this.id = uomId;
    }
    public UOM() {
        super();
    }
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "uom_seq")
    @SequenceGenerator(name = "uom_seq", sequenceName = "uom_sequence", allocationSize = 1)
    private Long id;
    private String code;
    private String description;
}
