package online.devplanet.crud_application.CustomAnnotations.Validator;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import online.devplanet.crud_application.CustomAnnotations.Annotations.ReservationDateConstraint;

import java.time.LocalDate;

public class ReservationDateValidator implements ConstraintValidator<ReservationDateConstraint, LocalDate> {

    @Override
    public boolean isValid(LocalDate date, ConstraintValidatorContext context) {

        context.disableDefaultConstraintViolation();

        // Case : Date is a Tuesday
        if (date.getDayOfWeek().getValue() == 2) {
            context.buildConstraintViolationWithTemplate("Reservations cannot be made on Tuesdays.")
                    .addConstraintViolation();
            return false;
        }

        // If all checks pass, return true
        return true;
    }
}