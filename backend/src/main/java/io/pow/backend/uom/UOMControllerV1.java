package io.pow.backend.uom;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/uoms/v1")
public class UOMControllerV1 {

    @Autowired
    private final UOMService uomService;

    public UOMControllerV1(UOMService uomService) {
        this.uomService = uomService;
    }

    @PostMapping
    public ResponseEntity<UOMResponse> createUOM(@RequestBody UOMRequest uomRequest) {
        uomService.createUOM(uomRequest);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new UOMResponse(UOMMessages.UOM_CREATED.getMessage()));
    }

    public record UOMRequest(String code, String description) {
    }

    public record UOMResponse(String message) {
    }
}
