package io.pow.backend.product;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import io.pow.backend.product.entity.Product;
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
        assertThat(response.getBody().error()).isEqualTo("Product sku is required");

        response = createProduct(null, "description1");
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().code()).isEqualTo("P40002");
        assertThat(response.getBody().error()).isEqualTo("Product sku is required");
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
        String sku = "codeA";
        ResponseEntity<ProductTestResponse> response = createProduct(sku, "description1");
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().message()).isEqualTo("Product created successfully");

        response = createProduct(sku, "description2");
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().code()).isEqualTo("P40004");
        assertThat(response.getBody().error()).isEqualTo("Product sku already exists");
    }

    @Test
    void getProducts_AllProducts() throws Exception {
        createProduct("code1list", "description1");
        createProduct("code2list", "description2");

        String url = baseUrl + "/products/v1";
        ResponseEntity<Product[]> response = restTemplate.getForEntity(url, Product[].class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().length).isGreaterThanOrEqualTo(2);
    }

    @Test
    void getProducts_ByCode() throws Exception {
        String sku = "codeSpecific";
        createProduct(sku, "descriptionSpecific");

        String url = baseUrl + "/products/v1/" + sku;
        ResponseEntity<ProductTestResponse[]> response = restTemplate.getForEntity(url, ProductTestResponse[].class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().length).isEqualTo(1);
        assertThat(response.getBody()[0].sku()).isEqualTo(sku);
    }

    private ResponseEntity<ProductTestResponse> createProduct(String sku, String description) {
        String url = baseUrl + "/products/v1";
        return this.restTemplate.postForEntity(
                url,
                new ProductTestRequest(sku, description),
                ProductTestResponse.class);
    }

    public record ProductTestRequest(String sku, String description,List<ProductUOMTestRequest> productUOMs) {
        public ProductTestRequest(List<ProductUOMTestRequest> productUOMs) {
            this(null, null, productUOMs);
        }
        public ProductTestRequest(String sku, String description) {
            this(sku, description, null);
        }
    }
    public record ProductTestResponse(String sku, String message, String error,String code) {
        public ProductTestResponse(String sku, String message, String error) {
            this(sku, message, error, null);
        }
    }
    public record ProductUOMTestRequest(String sku, double unitPrice) {
    }
}
