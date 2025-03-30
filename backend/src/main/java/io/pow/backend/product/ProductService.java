package io.pow.backend.product;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.pow.backend.product.ProductControllerV1.ProductRequest;

@Service
@Validated
public class ProductService {

    private static final Logger logger = LoggerFactory.getLogger(ProductService.class);

    @Autowired
    private ProductRepository productRepository;

    public void createProduct(@ProductValidate ProductRequest productRequest) {
        Product product = new Product();
        product.setCode(productRequest.code());
        product.setDescription(productRequest.description());
        productRepository.save(product);
        logger.info(ProductMessages.PRODUCT_CREATED.getMessage());
    }
}
