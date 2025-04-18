package io.pow.backend.txn;

import java.math.BigDecimal;

import io.pow.backend.product.entity.ProductPrice;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.SequenceGenerator;
import lombok.Data;

@Data
@Entity
public class TxnDetail {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "txn_detail_seq")
    @SequenceGenerator(name = "txn_detail_seq", sequenceName = "txn_detail_sequence", allocationSize = 1)
    private Long id;
    @ManyToOne
    private Txn txn;
    @ManyToOne
    private ProductPrice productUOM;
    private BigDecimal unitPrice;
    private BigDecimal totalAmount;
    private Integer qty;

    public TxnDetail() {
    }
}
