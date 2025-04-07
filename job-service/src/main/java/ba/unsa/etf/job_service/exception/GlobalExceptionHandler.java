package ba.unsa.etf.job_service.exception;

import java.util.ArrayList;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

// Ova klasa automatski obrađuje izuzetke u aplikaciji
@RestControllerAdvice
public class GlobalExceptionHandler {

  // Obradjujemo greske validacije
  @ExceptionHandler(MethodArgumentNotValidException.class)
  public ResponseEntity<Object> handleValidationExceptions(MethodArgumentNotValidException ex) {
    List<FieldError> errors = ex.getBindingResult().getFieldErrors();
    List<String> errorMessages = new ArrayList<>();

    // Dodajemo sve greške u listu
    for (FieldError error : errors) {
      errorMessages.add(error.getField() + ": " + error.getDefaultMessage());
    }

    // Vraćamo odgovarajući odgovor sa listom grešaka
    return new ResponseEntity<>(errorMessages, HttpStatus.BAD_REQUEST);
  }

  // Obrada drugih izuzetaka, po potrebi
  @ExceptionHandler(Exception.class)
  public ResponseEntity<Object> handleGeneralExceptions(Exception ex) {
    List<String> errorMessages = new ArrayList<>();
    errorMessages.add(ex.getMessage());

    return new ResponseEntity<>(errorMessages, HttpStatus.INTERNAL_SERVER_ERROR);
  }
}
