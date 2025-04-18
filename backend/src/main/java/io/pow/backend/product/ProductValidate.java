package io.pow.backend.product;

import jakarta.validation.Constraint;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.springframework.beans.factory.annotation.Autowired;

import io.pow.backend.product.dto.ProductRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;




@Constraint(validatedBy = ProductValidator.class)
@Target({ ElementType.TYPE, ElementType.PARAMETER })
@Retention(RetentionPolicy.RUNTIME)
public @interface ProductValidate {
    String message() default "Invalid product";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}

class ProductValidator implements ConstraintValidator<ProductValidate, ProductRequest> {
    private static final Logger logger = LoggerFactory.getLogger(ProductValidator.class);

    @Autowired
    private ProductRepository productRepository;

    @Override
    public boolean isValid(ProductRequest productRequest, ConstraintValidatorContext context) {
        if (productRequest.getSku() == null || productRequest.getSku().isEmpty()) {
            logger.error(ProductMessages.PRODUCT_CODE_REQUIRED.getMessage());
            throw new ProductException(ProductMessages.PRODUCT_CODE_REQUIRED);
        }
        if (productRequest.getDescription() == null || productRequest.getDescription().isEmpty()) {
            logger.error(ProductMessages.PRODUCT_DESCRIPTION_REQUIRED.getMessage());
            throw new ProductException(ProductMessages.PRODUCT_DESCRIPTION_REQUIRED);
        }
        if (productRepository.existsBySku(productRequest.getSku())) {
            logger.error(ProductMessages.PRODUCT_ALREADY_EXISTS.getMessage());
            throw new ProductException(ProductMessages.PRODUCT_ALREADY_EXISTS);
        }
        return true;
    }
}
