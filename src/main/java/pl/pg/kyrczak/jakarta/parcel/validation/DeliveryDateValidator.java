package pl.pg.kyrczak.jakarta.parcel.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import java.time.LocalDate;

public class DeliveryDateValidator implements ConstraintValidator<ValidDeliveryDate, LocalDate> {

    private static final LocalDate MINIMUM_DATE = LocalDate.of(2000, 1, 1);

    @Override
    public void initialize(ValidDeliveryDate constraintAnnotation) {
        // No initialization required for this validation logic
    }

    @Override
    public boolean isValid(LocalDate deliveryDate, ConstraintValidatorContext context) {
        if (deliveryDate == null) {
            return true; // Let @NotNull handle null checks
        }
        return deliveryDate.isAfter(MINIMUM_DATE);
    }
}
