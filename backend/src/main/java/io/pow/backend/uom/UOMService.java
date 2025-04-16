package io.pow.backend.uom;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import io.pow.backend.uom.UOMControllerV1.UOMRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Optional;

@Service
@Validated
public class UOMService {

    private static final Logger logger = LoggerFactory.getLogger(UOMService.class);

    @Autowired
    private UOMRepository uomRepository;

    public void createUOM(@UOMValidate UOMRequest uomRequest) {
        UoM uom = new UoM();
        uom.setCode(uomRequest.code());
        uom.setDescription(uomRequest.description());
        uomRepository.save(uom);
        logger.info(UOMMessages.UOM_CREATED.getMessage());
    }

    public List<UoM> listUOMs(String codeStr) {
        Optional<String> code = Optional.ofNullable(codeStr);
        if (code.isEmpty()) {
            return uomRepository.findAll();
        } else {
            return uomRepository.findByCode(code.get())
                    .map(List::of)
                    .orElse(List.of());
        }
    }

}
