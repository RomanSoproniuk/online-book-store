package book.store.onlinebookstore.validation;

import book.store.onlinebookstore.dto.UserRegistrationRequestDto;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class FieldMatchValidator implements ConstraintValidator<FieldMatch,
        UserRegistrationRequestDto> {

    @Override
    public boolean isValid(UserRegistrationRequestDto value, ConstraintValidatorContext context) {
        return value.getPassword() != null
                && value.getPassword().equals(value.getRepeatedPassword());
    }
}
