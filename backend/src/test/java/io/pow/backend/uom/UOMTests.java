package io.pow.backend.uom;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import jakarta.annotation.PostConstruct;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class UOMTests {
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
    void createUOM_Success() throws Exception {
        ResponseEntity<UOMTestResponse> response = createUOM("code1", "description1");
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().message()).isEqualTo("UOM created successfully");
    }

    @Test
    void createUOM_CodeRequired() throws Exception {
        ResponseEntity<UOMTestResponse> response = createUOM("", "description1");
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().code()).isEqualTo("U40002");
        assertThat(response.getBody().error()).isEqualTo("UOM code is required");

        response = createUOM(null, "description1");
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().code()).isEqualTo("U40002");
        assertThat(response.getBody().error()).isEqualTo("UOM code is required");
    }

    @Test
    void createUOM_DescriptionRequired() throws Exception {
        ResponseEntity<UOMTestResponse> response = createUOM("code1", "");
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().code()).isEqualTo("U40003");
        assertThat(response.getBody().error()).isEqualTo("UOM description is required");

        response = createUOM("code1", null);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().code()).isEqualTo("U40003");
        assertThat(response.getBody().error()).isEqualTo("UOM description is required");
    }

    @Test
    void createUOM_CodeAlreadyExists() throws Exception {
        String code = "codeA";
        ResponseEntity<UOMTestResponse> response = createUOM(code, "description1");
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().message()).isEqualTo("UOM created successfully");

        response = createUOM(code, "description2");
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().code()).isEqualTo("U40001");
        assertThat(response.getBody().error()).isEqualTo("UOM code already exists");
    }

    @Test
    void listUOMs_AllUOMs() throws Exception {
        // Create some UOMs
        createUOM("code1list", "description1");
        createUOM("code2list", "description2");

        // Test listing all UOMs
        String url = baseUrl + "/uoms/v1";
        ResponseEntity<UoM[]> response = restTemplate.getForEntity(url, UoM[].class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().length).isGreaterThanOrEqualTo(2);
    }

    @Test
    void listUOMs_ByCode() throws Exception {
        // Create a UOM
        String code = "codeAAA";
        createUOM(code, "descriptionA");

        // Test listing UOM by code
        String url = baseUrl + "/uoms/v1/" + code;
        ResponseEntity<UoM[]> response = restTemplate.getForEntity(url, UoM[].class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().length).isEqualTo(1);
        assertThat(response.getBody()[0].getCode()).isEqualTo(code);
    }

    private ResponseEntity<UOMTestResponse> createUOM(String code, String description) {
        String url = baseUrl + "/uoms/v1";
        return this.restTemplate.postForEntity(
                url,
                new UOMTestRequest(code, description),
                UOMTestResponse.class);
    }

    public record UOMTestRequest(String code, String description) {
    }

    public record UOMTestResponse(String code, String message, String error) {
    }
}
