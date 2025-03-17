package online.devplanet.crud_application.CustomAnnotations.Validator;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import online.devplanet.crud_application.CustomAnnotations.Annotations.Trimmed;

public class TrimmedValidator implements ConstraintValidator<Trimmed, String> {

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value == null) {
            return true;
        }
        return value.equals(value.trim());
    }
}
