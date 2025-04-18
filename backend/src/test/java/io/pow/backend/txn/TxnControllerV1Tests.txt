package io.pow.backend.txn;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import io.pow.backend.product.ProductTests.ProductTestRequest;
import io.pow.backend.product.ProductTests.ProductTestResponse;
import io.pow.backend.product.ProductTests.ProductUOMTestRequest;
import io.pow.backend.uom.UOMTests.UOMTestRequest;
import io.pow.backend.uom.UOMTests.UOMTestResponse;
import jakarta.annotation.PostConstruct;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class TxnControllerV1Tests {

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
    void createTxn_Success() {
        String productCode = "productCode1",uomCode = "uomCode1";
        createProduct(productCode, "productDescription1");
        createUOM(uomCode, "uomCode1Description1");
        ProductTestRequest productRequest = new ProductTestRequest(
            List.of(new ProductUOMTestRequest(uomCode, 10.99)));
        createProductUOMs(productCode,productRequest);
        TxnDetailTestRequest detailRequest = new TxnDetailTestRequest(productCode, uomCode, 10);
        TxnTestRequest txnRequest = new TxnTestRequest(List.of(detailRequest));
        ResponseEntity<TxnTestResponse> response = createTxn(txnRequest);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().message()).isEqualTo("Transaction created successfully");
    }

    @Test
    void createTxn_ProductUOMNotFound() {
        TxnDetailTestRequest detailRequest = new TxnDetailTestRequest("invalidProductCode", "invalidUOMCode", 10);
        TxnTestRequest txnRequest = new TxnTestRequest(List.of(detailRequest));

        ResponseEntity<TxnTestResponse> response = createTxn(txnRequest);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().code()).isEqualTo("P40007");
        assertThat(response.getBody().error()).isEqualTo("Product or Unit of Measure not found");
    }

    private ResponseEntity<TxnTestResponse> createTxn(TxnTestRequest txnRequest) {
        String url = baseUrl + "/txns/v1";
        return this.restTemplate.postForEntity(url, txnRequest, TxnTestResponse.class);
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



    public record TxnTestRequest(List<TxnDetailTestRequest> txnDetails) {
    }

    public record TxnDetailTestRequest(String productCode, String uomCode, Integer qty) {
    }

    public record TxnTestResponse(String code, String message, String error) {
    }

    
}
