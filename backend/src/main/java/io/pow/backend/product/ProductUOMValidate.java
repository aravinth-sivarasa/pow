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
import io.pow.backend.product.dto.ProductUOMRequest;
import io.pow.backend.uom.UOM;
import io.pow.backend.uom.UOMMessages;
import io.pow.backend.uom.UOMRepository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Constraint(validatedBy = ProductUOMValidator.class)
@Target({ ElementType.TYPE, ElementType.PARAMETER })
@Retention(RetentionPolicy.RUNTIME)
public @interface ProductUOMValidate {
    String message() default "Invalid product";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}

class ProductUOMValidator implements ConstraintValidator<ProductUOMValidate, ProductRequest> {
    private static final Logger logger = LoggerFactory.getLogger(ProductUOMValidator.class);

    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private UOMRepository uomRepository;

    @Override
    public boolean isValid(ProductRequest productRequest, ConstraintValidatorContext context) {
        validateProduct(productRequest);
        validateUOMs(productRequest);
        return true;
    }

    private void validateProduct(ProductRequest productRequest) {
        if (productRequest.getCode() == null || productRequest.getCode().isEmpty()) {
            logger.error(ProductMessages.PRODUCT_CODE_REQUIRED.getMessage());
            throw new ProductException(ProductMessages.PRODUCT_CODE_REQUIRED);
        }
        Product product = productRepository.findByCode(productRequest.getCode())
                .orElseThrow(() -> {
                    logger.error(ProductMessages.PRODUCT_NOT_FOUND.getMessage());
                    return new ProductException(ProductMessages.PRODUCT_NOT_FOUND);
                });
        productRequest.setProductId(product.getId());
    }

    private void validateUOMs(ProductRequest productRequest) {
        if (productRequest.getProductUOMs() == null || productRequest.getProductUOMs().isEmpty()) {
            logger.error(ProductMessages.PRODUCT_UOM_REQUIRED.getMessage());
            throw new ProductException(ProductMessages.PRODUCT_UOM_REQUIRED);
        }
        for (ProductUOMRequest productUOM : productRequest.getProductUOMs()) {
            UOM uom = uomRepository.findByCode(productUOM.getCode())
                    .orElseThrow(() -> {
                        logger.error(UOMMessages.UOM_NOT_FOUND.getMessage());
                        return new ProductException(UOMMessages.UOM_NOT_FOUND.getCode(),
                                UOMMessages.UOM_NOT_FOUND.getMessage());
                    });
            productUOM.setUOMID(uom.getId());

        }
    }
}
