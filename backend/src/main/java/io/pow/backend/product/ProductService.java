package io.pow.backend.product;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import io.pow.backend.product.ProductControllerV1.ProductRequest;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    public void createProduct(ProductRequest productRequest) {
        if (productRequest.code() == null || productRequest.code().isEmpty()) {
            throw new ProductException(ProductMessages.PRODUCT_CODE_REQUIRED);
        }
        if (productRequest.description() == null || productRequest.description().isEmpty()) {
            throw new ProductException(ProductMessages.PRODUCT_DESCRIPTION_REQUIRED);
        }
        Product product = new Product();
        product.setCode(productRequest.code());
        product.setDescription(productRequest.description());

        if (productRepository.existsByCode(product.getCode())) {
            throw new ProductException(ProductMessages.PRODUCT_ALREADY_EXISTS);
        }
        productRepository.save(product);
    }
}
