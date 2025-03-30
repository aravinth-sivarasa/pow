package io.pow.backend.uom;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import io.pow.backend.uom.UOMControllerV1.UOMRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
@Validated
public class UOMService {

    private static final Logger logger = LoggerFactory.getLogger(UOMService.class);

    @Autowired
    private UOMRepository uomRepository;

    public void createUOM(@UOMValidate UOMRequest uomRequest) {
        UOM uom = new UOM();
        uom.setCode(uomRequest.code());
        uom.setDescription(uomRequest.description());
        uomRepository.save(uom);
        logger.info(UOMMessages.UOM_CREATED.getMessage());
    }



}
