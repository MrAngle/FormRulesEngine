package apiv2.dataprocurment.api.validation;

import apiv2.dataprocurment.api.validation.impl.VehicleDataRequestValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = VehicleDataRequestValidator.class)
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidVehicleData {
    String message() default "Either VIN or registration number must be provided, not both.";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}