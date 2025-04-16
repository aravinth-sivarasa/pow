package io.pow.backend.txn;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import io.pow.backend.product.entity.ProductUoM;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
@Validated
public class TxnService {

    private static final Logger logger = LoggerFactory.getLogger(TxnService.class);

    @Autowired
    private TxnRepository txnRepository;
    @Autowired
    private TxnDetailRepository txnDetailRepository;

    @Transactional
    public void createTxn(@TxnValidate TxnRequest txnRequest) {
        Txn txn = new Txn();
        txn.setTxnDate(new Date());
        final Txn saveTxn = txnRepository.save(txn);
        txnRequest.getTxnDetails().forEach(txnDetailRequest -> {
            TxnDetail txnDetail = new TxnDetail();
            ProductUoM productUOM = txnDetailRequest.getProductUOM();
            txnDetail.setTxn(saveTxn);
            txnDetail.setProductUOM(txnDetailRequest.getProductUOM());
            txnDetail.setQty(txnDetailRequest.getQty());
            txnDetail.setUnitPrice(productUOM.getUnitPrice());
            txnDetailRepository.save(txnDetail);
        });
        logger.info(TxnMessages.TXN_CREATED.getMessage());
    }

    public record TxnResponse(String message) {
    }
}
