package io.pow.backend.txn;

import java.util.Map;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class TxnExceptionHandler {

    @ExceptionHandler(TxnException.class)
    public ResponseEntity<Map<String,String>> handleProductException(TxnException ex) {
        
        Map<String, String> errorResponse = Map.of(
                "error", ex.getMessage(),
                "code",ex.getCode()
        );
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
    }
}
