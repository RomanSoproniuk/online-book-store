package book.store.onlinebookstore.dto;

import book.store.onlinebookstore.validation.FieldMatch;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@FieldMatch(password = "password",
        repeatedPassword = "repeatedPassword",
        message = "Password and repeated password must match")
@Data
public class UserRegistrationRequestDto {
    private Long id;
    @NotNull
    @Email
    private String email;
    @Size(min = 8, max = 32)
    private String password;
    @Size(min = 8, max = 32)
    private String repeatedPassword;
    @NotNull
    @Size(min = 2, max = 32)
    private String firstName;
    @Size(min = 2, max = 32)
    private String lastName;
    private String shippingAddress;
}
