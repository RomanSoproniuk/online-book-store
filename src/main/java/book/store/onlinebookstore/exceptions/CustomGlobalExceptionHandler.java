package book.store.onlinebookstore.exceptions;

import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.Map;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class CustomGlobalExceptionHandler extends ResponseEntityExceptionHandler {
    private static final String TIMESTAMP_PARAM = "timestamp";
    private static final String HTTP_STATUS_PARAM = "timestamp";
    private static final String ERRORS_PARAM = "errors";

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(
            MethodArgumentNotValidException ex,
            HttpHeaders headers,
            HttpStatusCode status,
            WebRequest request) {
        Map<String, Object> body = new LinkedHashMap<>();
        body.put(TIMESTAMP_PARAM, LocalDateTime.now());
        body.put(HTTP_STATUS_PARAM, HttpStatus.BAD_REQUEST);
        body.put(ERRORS_PARAM,ex.getBindingResult().getAllErrors().stream()
                .map(this::getErrorMessage)
                .toList());
        return new ResponseEntity<>(body, headers, status);
    }

    private String getErrorMessage(ObjectError objectError) {
        if (objectError instanceof FieldError) {
            String field = ((FieldError) objectError).getField();
            String message = objectError.getDefaultMessage();
            return field + " " + message;
        }
        return objectError.getDefaultMessage();
    }
}
