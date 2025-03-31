package io.pow.backend.txn;

import java.util.List;

import io.pow.backend.product.ProductUOM;
import lombok.Data;

@Data
public class TxnRequest {
    private List<TxnDetailRequest> txnDetails;
}
@Data
class TxnDetailRequest {
    private String productCode;
    private String uomCode;
    private Integer qty;
    private ProductUOM productUOM;
}