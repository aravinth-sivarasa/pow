package io.pow.backend.txn;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


import io.pow.backend.txn.TxnService.TxnResponse;

@RestController
@RequestMapping("/txns/v1")
public class TxnControllerV1 {

    @Autowired
    private TxnService txnService;

    @PostMapping
    public ResponseEntity<TxnResponse> createTxn(@RequestBody TxnRequest txnRequest) {
        txnService.createTxn(txnRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(new TxnResponse(
                TxnMessages.TXN_CREATED.getMessage()));
    }


}
