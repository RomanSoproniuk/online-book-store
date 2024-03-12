package book.store.onlinebookstore.exceptions;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
public class RegistrationException extends Exception {
    private String message;
    public RegistrationException(String message) {
        this.message = message;
    }
}
