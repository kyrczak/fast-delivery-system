package pl.pg.kyrczak.jakarta.parcel.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = DeliveryDateValidator.class)
@Target({ElementType.FIELD, ElementType.METHOD, ElementType.PARAMETER, ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidDeliveryDate {
    String message() default "Delivery date must be after January 1, 2000";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
