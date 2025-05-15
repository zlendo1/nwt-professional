package ba.unsa.etf.content_service.exception;

import java.util.HashMap;
import java.util.Map;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

  @ExceptionHandler(MethodArgumentNotValidException.class)
  public ResponseEntity<Object> handleValidationException(MethodArgumentNotValidException ex) {
    Map<String, Object> errorResponse = new HashMap<>();
    Map<String, String> fieldErrors = new HashMap<>();

    // Samo prvi error po polju — sprječavamo duplikate
    ex.getBindingResult()
        .getFieldErrors()
        .forEach(
            error -> {
              fieldErrors.putIfAbsent(error.getField(), error.getDefaultMessage());
            });

    errorResponse.put("error", "validation");
    errorResponse.put("messages", fieldErrors);

    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
  }

  @ExceptionHandler(RuntimeException.class)
  public ResponseEntity<Object> handleRuntimeException(RuntimeException ex) {
    Map<String, Object> response = new HashMap<>();
    response.put("error", "runtime");
    response.put("message", ex.getMessage());
    HttpStatus status = HttpStatus.BAD_REQUEST;
    if (ex.getMessage().startsWith("User not found")) {
      status = HttpStatus.NOT_FOUND;
    }
    return new ResponseEntity<>(response, status);
  }
}
