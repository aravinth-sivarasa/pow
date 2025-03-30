package io.pow.backend.uom;

import jakarta.validation.Constraint;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.springframework.beans.factory.annotation.Autowired;

import io.pow.backend.uom.UOMControllerV1.UOMRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;



@Constraint(validatedBy = UOMValidator.class)
@Target({ ElementType.TYPE, ElementType.PARAMETER })
@Retention(RetentionPolicy.RUNTIME)
public @interface UOMValidate {
    String message() default "Invalid UOM";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}

class UOMValidator implements ConstraintValidator<UOMValidate, UOMRequest> {
    private static final Logger logger = LoggerFactory.getLogger(UOMValidator.class);

    @Autowired
    private UOMRepository UOMRepository;

    @Override
    public boolean isValid(UOMRequest UOMRequest, ConstraintValidatorContext context) {
        if (UOMRequest.code() == null || UOMRequest.code().isEmpty()) {
            logger.error(UOMMessages.UOM_CODE_REQUIRED.getMessage());
            throw new UOMException(UOMMessages.UOM_CODE_REQUIRED);
        }
        if (UOMRequest.description() == null || UOMRequest.description().isEmpty()) {
            logger.error(UOMMessages.UOM_DESCRIPTION_REQUIRED.getMessage());
            throw new UOMException(UOMMessages.UOM_DESCRIPTION_REQUIRED);
        }
        if (UOMRepository.existsByCode(UOMRequest.code())) {
            logger.error(UOMMessages.UOM_ALREADY_EXISTS.getMessage());
            throw new UOMException(UOMMessages.UOM_ALREADY_EXISTS);
        }
        return true;
    }
}
