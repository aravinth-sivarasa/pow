package io.pow.backend.product;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.pow.backend.product.dto.ProductRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
@Validated
public class ProductService {

    private static final Logger logger = LoggerFactory.getLogger(ProductService.class);

    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private ProductUOMRepository productUOMRepository;
    @Autowired
    private ObjectMapper objectMapper;

    public void createProduct(@ProductValidate ProductRequest productRequest) {
        try {
            String text = objectMapper.writeValueAsString(productRequest);
            Product product = objectMapper.readValue(text, Product.class);
            productRepository.save(product);
            logger.info(ProductMessages.PRODUCT_CREATED.getMessage());
        } catch (JsonProcessingException e) {
            logger.error(ProductMessages.PRODUCT_JSON_PARSE.getMessage(), e);
            throw new ProductException(ProductMessages.PRODUCT_JSON_PARSE);
        }
    }

    public void createProductUOMs(@ProductUOMValidate ProductRequest productRequest) {
        Long productId = productRequest.getProductId();
        productRequest.getProductUOMs().forEach(productUOMRequest -> {
            Long uomId = productUOMRequest.getUOMID();
            ProductUOM productUOM = new ProductUOM(
                    productId, uomId, productUOMRequest.getUnitPrice());
            productUOMRepository.save(productUOM);
        });
        logger.info(ProductMessages.PRODUCT_UOM_CREATED.getMessage());
    }

    public record ProductResponse(String message) {
    }
}
