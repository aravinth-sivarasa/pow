package io.pow.backend.product;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import io.pow.backend.uom.UOMTests.UOMTestRequest;
import io.pow.backend.uom.UOMTests.UOMTestResponse;
import jakarta.annotation.PostConstruct;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class ProductTests {
    @LocalServerPort
    private int port;
    @Autowired
    private TestRestTemplate restTemplate;
    private String baseUrl;

    @PostConstruct
    public void init() {
        baseUrl = "http://localhost:" + port;
    }

    @Test
    void createProduct_Success() throws Exception {
        ResponseEntity<ProductTestResponse> response = createProduct("code1", "description1");
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().message()).isEqualTo("Product created successfully");

    }

    @Test
    void createProduct_CodeRequired() throws Exception {
        ResponseEntity<ProductTestResponse> response = createProduct("", "description1");
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().code()).isEqualTo("P40002");
        assertThat(response.getBody().error()).isEqualTo("Product code is required");

        response = createProduct(null, "description1");
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().code()).isEqualTo("P40002");
        assertThat(response.getBody().error()).isEqualTo("Product code is required");
    }

    @Test
    void createProduct_DescriptionRequired() throws Exception {
        ResponseEntity<ProductTestResponse> response = createProduct("code1", "");
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().code()).isEqualTo("P40003");
        assertThat(response.getBody().error()).isEqualTo("Product description is required");

        response = createProduct("code1", null);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().code()).isEqualTo("P40003");
        assertThat(response.getBody().error()).isEqualTo("Product description is required");
    }

    @Test
    void createProduct_CodeAlreadyExists() throws Exception {
        String code = "codeA";
        ResponseEntity<ProductTestResponse> response = createProduct(code, "description1");
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().message()).isEqualTo("Product created successfully");

        response = createProduct(code, "description2");
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().code()).isEqualTo("P40004");
        assertThat(response.getBody().error()).isEqualTo("Product code already exists");
    }

    @Test
    void createProductUOMs_Success() throws Exception {
        String code = "code1UOM";
        createProduct(code, "description1"); // Ensure the product exists
        createUOM("uomCode1", "uomCode1");
        ProductUOMTestRequest uomRequest = new ProductUOMTestRequest("uomCode1", 100.50);
        ProductTestRequest productRequest = new ProductTestRequest(List.of(uomRequest));

        ResponseEntity<ProductTestResponse> response = createProductUOMs(code, productRequest);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().message()).isEqualTo("Product UOM created successfully");
    }

    @Test
    void createProductUOMs_ProductNotFound() throws Exception {
        String code = "nonExistentCode";

        ProductUOMTestRequest uomRequest = new ProductUOMTestRequest("uomCode1", 100.50);
        ProductTestRequest productRequest = new ProductTestRequest(List.of(uomRequest));

        ResponseEntity<ProductTestResponse> response = createProductUOMs(code, productRequest);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().code()).isEqualTo("P40001");
        assertThat(response.getBody().error()).isEqualTo("Product not found");
    }

    @Test
    void createProductUOMs_UOMNotFound() throws Exception {
        String code = "code2UOM";
        createProduct(code, "description1"); // Ensure the product exists

        ProductUOMTestRequest uomRequest = new ProductUOMTestRequest("nonExistentUOM", 100.50);
        ProductTestRequest productRequest = new ProductTestRequest( List.of(uomRequest));

        ResponseEntity<ProductTestResponse> response = createProductUOMs(code, productRequest);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().code()).isEqualTo("U40004");
        assertThat(response.getBody().error()).isEqualTo("UOM not found");
    }

    private ResponseEntity<ProductTestResponse> createProduct(String code, String description) {
        String url = baseUrl + "/products/v1";
        return this.restTemplate.postForEntity(
                url,
                new ProductTestRequest(code, description),
                ProductTestResponse.class);
    }
    private ResponseEntity<UOMTestResponse> createUOM(String code, String description) {
        String url = baseUrl + "/uoms/v1";
        return this.restTemplate.postForEntity(
                url,
                new UOMTestRequest(code, description),
                UOMTestResponse.class);
    }

    private ResponseEntity<ProductTestResponse> createProductUOMs(String code, ProductTestRequest productRequest) {
        String url = baseUrl + "/products/v1/" + code + "/uoms";
        return this.restTemplate.postForEntity(
                url,
                productRequest,
                ProductTestResponse.class);
    }

    public record ProductTestRequest(String code, String description,List<ProductUOMTestRequest> productUOMs) {
        public ProductTestRequest(List<ProductUOMTestRequest> productUOMs) {
            this(null, null, productUOMs);
        }
        public ProductTestRequest(String code, String description) {
            this(code, description, null);
        }
    }
    public record ProductTestResponse(String code, String message, String error) {
    }
    public record ProductUOMTestRequest(String code, double unitPrice) {
    }
}
