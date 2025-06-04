//package apiv2.dataprocurment.api.validation.impl;
//
//import apiv2.dataprocurment.api.validation.ValidPESEL;
//import io.micrometer.common.util.StringUtils;
//import jakarta.validation.ConstraintValidator;
//import jakarta.validation.ConstraintValidatorContext;
//
//public class PeselValidator implements ConstraintValidator<ValidPESEL, String> {
//
//    @Override
//    public boolean isValid(String value, ConstraintValidatorContext context) {
//        if (StringUtils.isBlank(value)) return true; // skip nulls, handled by @NotNull if needed
//
//        if (!value.matches("\\d{11}")) return false;
//
//        // TODO: Additional check for PESEL format
//        return true;
//    }
//}
