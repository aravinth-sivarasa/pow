package io.pow.backend.product;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.pow.backend.product.dto.ProductRequest;
import io.pow.backend.product.entity.Product;
import io.pow.backend.product.entity.ProductUOM;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

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
        UUID productId = productRequest.getProductId();
        productRequest.getProductUOMs().forEach(productUOMRequest -> {
            UUID uomId = productUOMRequest.getUOMID();
            ProductUOM productUOM = new ProductUOM(
                    productId, uomId, productUOMRequest.getUnitPrice());
            productUOMRepository.save(productUOM);
        });
        logger.info(ProductMessages.PRODUCT_UOM_CREATED.getMessage());
    }

    public List<Product> listProducts(String codeStr) {
        Optional<String> code = Optional.ofNullable(codeStr);
        if (code.isEmpty()) {
            return productRepository.findAll();
        } else {
            return productRepository.findByCode(code.get())
                    .map(List::of)
                    .orElse(List.of());
        }
    }

    public record ProductResponse(String message) {
    }
}
