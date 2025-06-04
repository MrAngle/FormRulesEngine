//package apiv2.dataprocurment.api.validation;
//
//import apiv2.dataprocurment.api.validation.impl.NipValidator;
//import jakarta.validation.Constraint;
//import jakarta.validation.Payload;
//
//import java.lang.annotation.*;
//
//@Documented
//@Constraint(validatedBy = NipValidator.class)
//@Target({ ElementType.FIELD })
//@Retention(RetentionPolicy.RUNTIME)
//public @interface ValidNIP {
//    String message() default "Invalid NIP number";
//
//    Class<?>[] groups() default {};
//
//    Class<? extends Payload>[] payload() default {};
//}
