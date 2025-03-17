package online.devplanet.crud_application.CustomAnnotations.Annotations;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import online.devplanet.crud_application.CustomAnnotations.Validator.TrimmedValidator;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = TrimmedValidator.class)
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)

public @interface Trimmed {
    String message() default "Field cannot contain leading or trailing whitespaces.";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
