package io.pow.backend.uom;

import java.util.Map;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class UOMExceptionHandler {

    @ExceptionHandler(UOMException.class)
    public ResponseEntity<Map<String,String>> handleProductException(UOMException ex) {
        
        Map<String, String> errorResponse = Map.of(
                "error", ex.getMessage(),
                "code",ex.getCode()
        );
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
    }
}
