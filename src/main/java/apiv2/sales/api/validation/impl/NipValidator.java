package apiv2.sales.api.validation.impl;

import apiv2.sales.api.validation.ValidNIP;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.apache.commons.lang3.StringUtils;

public class NipValidator implements ConstraintValidator<ValidNIP, String> {

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (StringUtils.isBlank(value)) {
            return true; // użyj @NotBlank jeśli chcesz wymusić obecność
        }

        value = value.replaceAll("-", ""); // akceptujemy NIP z myślnikami
        if (!value.matches("\\d{10}")) {
            return false;
        }

        int[] weights = {6, 5, 7, 2, 3, 4, 5, 6, 7};
        int sum = 0;
        for (int i = 0; i < weights.length; i++) {
            sum += weights[i] * Character.getNumericValue(value.charAt(i));
        }

        int control = sum % 11;
        if (control == 10) {
            control = 0;
        }

        return control == Character.getNumericValue(value.charAt(9));
    }
}
