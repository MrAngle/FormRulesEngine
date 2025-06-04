package apiv2.sales.api.validation.impl;

import apiv2.sales.api.validation.ValidIdentificationNumber;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class IdentificationNumberValidator implements ConstraintValidator<ValidIdentificationNumber, String> {

//    private final SalesApiService salesApiService;
    private final PeselValidator peselValidator = new PeselValidator();
    private final NipValidator nipValidator = new NipValidator();

//    @Override
//    public boolean isValid(InitSalesRequest value, ConstraintValidatorContext context) {
//        return peselValidator.isValid(value.getPesel(), context)
//                || nipValidator.isValid(value.getNip(), context);
//    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        return peselValidator.isValid(value, context)
                || nipValidator.isValid(value, context);
    }
}
