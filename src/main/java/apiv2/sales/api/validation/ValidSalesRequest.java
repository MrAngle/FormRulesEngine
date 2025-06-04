package apiv2.sales.api.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = InitSalesRequestValidator.class)
@Target({ ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidSalesRequest {
    String message() default "Invalid combination of fields for businessFlow";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}

