//package apiv2.dataprocurment.api.validation.impl;
//
//import apiv2.dataprocurment.api.validation.ValidIdentificationNumber;
//import apiv2.sales.api.service.SalesApiService;
//import jakarta.validation.ConstraintValidator;
//import jakarta.validation.ConstraintValidatorContext;
//import lombok.RequiredArgsConstructor;
//import org.springframework.stereotype.Component;
//
//@Component
//@RequiredArgsConstructor
//public class IdentificationNumberValidator implements ConstraintValidator<ValidIdentificationNumber, String> {
//
//    private final SalesApiService salesApiService;
//    private final PeselValidator peselValidator = new PeselValidator();
//    private final NipValidator nipValidator = new NipValidator();
//
//    @Override
//    public boolean isValid(String value, ConstraintValidatorContext context) {
//        return peselValidator.isValid(value, context)
//                || nipValidator.isValid(value, context);
//    }
//}
