package io.pow.backend.currency;

import java.math.BigDecimal;
import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

@Data
@Entity
public class Currency {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID token;
    @Column(unique = true, nullable = false)
    private String code;
    private String name;
    private String symbol;
    private boolean baseCurrency;
    private String country;
    private BigDecimal buyRate;
    private BigDecimal sellRate;

    public Currency() {
        super();
    }
    public Currency(UUID currencyId) {
        this.token = currencyId;
    }
}
