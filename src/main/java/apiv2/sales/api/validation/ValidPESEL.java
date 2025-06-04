package apiv2.sales.api.validation;

import apiv2.sales.api.validation.impl.PeselValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = PeselValidator.class)
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidPESEL {
    String message() default "Invalid PESEL number";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
