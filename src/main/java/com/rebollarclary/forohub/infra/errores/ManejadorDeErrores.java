package com.rebollarclary.forohub.infra.errores;

import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.ValidationException;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;

@RestControllerAdvice
public class ManejadorDeErrores {

    private record ErrorResponse(String message) {
    }

    private record DatosErrorValidacion(String campo, String error) {
        public DatosErrorValidacion(FieldError error) {
            this(error.getField(), error.getDefaultMessage());
        }
    }

    /**
     * Maneja excepciones de tipo EntityNotFoundException (404).
     *
     * @param e la excepción lanzada
     * @return una respuesta con estado 404 y un mensaje de error
     */
    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<ErrorResponse> tratarError404(EntityNotFoundException e) {
        return ResponseEntity.status(404).body(new ErrorResponse(e.getMessage()));
    }

    /**
     * Maneja excepciones de tipo MethodArgumentNotValidException (400).
     *
     * @param e la excepción lanzada
     * @return una respuesta con estado 400 y una lista de errores de validación
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<List<DatosErrorValidacion>> tratarError400(MethodArgumentNotValidException e) {
        var errores = e.getFieldErrors()
                .stream()
                .map(DatosErrorValidacion::new)
                .toList();
        return ResponseEntity.badRequest().body(errores);
    }

    /**
     * Maneja excepciones de tipo ValidationException (400).
     *
     * @param e la excepción lanzada
     * @return una respuesta con estado 400 y un mensaje de error
     */
    @ExceptionHandler(ValidationException.class)
    public ResponseEntity<ErrorResponse> errorHandlerValidacionesDeNegocio(Exception e) {
        return ResponseEntity.badRequest().body(new ErrorResponse(e.getMessage()));
    }
}
