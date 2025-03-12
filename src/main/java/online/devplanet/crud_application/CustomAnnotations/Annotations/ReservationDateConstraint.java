package online.devplanet.crud_application.CustomAnnotations.Annotations;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import online.devplanet.crud_application.CustomAnnotations.Validator.ReservationDateValidator;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = ReservationDateValidator.class)
@Target({ElementType.METHOD, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)

public @interface ReservationDateConstraint {
    String message() default "Invalid Date. Date should be in the format yyyy-MM-dd";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
