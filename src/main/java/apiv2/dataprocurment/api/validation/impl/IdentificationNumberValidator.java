package apiv2.dataprocurment.api.validation.impl;

import apiv2.dataprocurment.api.validation.ValidIdentificationNumber;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class IdentificationNumberValidator implements ConstraintValidator<ValidIdentificationNumber, String> {
    private final PeselValidator peselValidator = new PeselValidator();
    private final NipValidator nipValidator = new NipValidator();

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        return peselValidator.isValid(value, context)
                || nipValidator.isValid(value, context);
    }
}
