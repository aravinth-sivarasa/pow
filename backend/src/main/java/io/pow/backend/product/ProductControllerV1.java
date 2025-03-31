package io.pow.backend.product;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.pow.backend.product.ProductService.ProductResponse;
import io.pow.backend.product.dto.ProductRequest;

@RestController
@RequestMapping("/products/v1")
public class ProductControllerV1 {

    private final ProductService productService;

    public ProductControllerV1(ProductService productService) {
        this.productService = productService;
    }

    @PostMapping
    public ResponseEntity<ProductResponse> createProduct(@RequestBody ProductRequest productRequest) {
        productService.createProduct(productRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(new ProductResponse(
                ProductMessages.PRODUCT_CREATED.getMessage()));
    }


    @PostMapping("{code}/uoms")
    public ResponseEntity<ProductResponse> createProductUOMs(@PathVariable String code, @RequestBody ProductRequest productRequest) {
        productRequest.setCode(code);
        productService.createProductUOMs(productRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(new ProductResponse(
                ProductMessages.PRODUCT_UOM_CREATED.getMessage()));
    }
    

}
