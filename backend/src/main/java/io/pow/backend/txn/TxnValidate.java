package io.pow.backend.txn;

import jakarta.validation.Constraint;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import io.pow.backend.product.ProductMessages;
import io.pow.backend.product.ProductUOMRepository;
import io.pow.backend.product.entity.ProductUOM;

@Constraint(validatedBy = TxnValidator.class)
@Target({ ElementType.TYPE, ElementType.PARAMETER })
@Retention(RetentionPolicy.RUNTIME)
public @interface TxnValidate {
    String message() default "Invalid Txn";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}

class TxnValidator implements ConstraintValidator<TxnValidate, TxnRequest> {
    private static final Logger logger = LoggerFactory.getLogger(TxnValidator.class);

    @Autowired
    private ProductUOMRepository productUOMRepository;

    @Override
    public boolean isValid(TxnRequest txnRequest, ConstraintValidatorContext context) {
        txnRequest.getTxnDetails()
                .forEach(txnDetail -> {
                    ProductUOM row = productUOMRepository.findByProductCodeAndUomCode(txnDetail.getProductCode(), txnDetail.getUomCode())
                            .orElseThrow(() -> {
                                logger.error(ProductMessages.PRODUCT_UOM_NOT_FOUND.getMessage());
                                return new TxnException(ProductMessages.PRODUCT_UOM_NOT_FOUND.getCode(),
                                        ProductMessages.PRODUCT_UOM_NOT_FOUND.getMessage());
                            });
                    txnDetail.setProductUOM(row);
                });
        return true;
    }



}
