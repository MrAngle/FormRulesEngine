package apiv2.dataprocurment.api.validation;

import apiv2.dataprocurment.api.validation.impl.IdentificationNumberValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = IdentificationNumberValidator.class)
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidIdentificationNumber {
    String message() default "One of PESEL, NIP or REGON must be provided";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
