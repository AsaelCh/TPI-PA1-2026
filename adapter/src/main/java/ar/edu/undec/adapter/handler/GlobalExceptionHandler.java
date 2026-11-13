
package ar.edu.undec.adapter.handler;

import empleado.exception.EmpleadoNoEncontradoException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import empleado.exception.NombreEmpleadoInvalidoException;
import empleado.exception.LegajoInvalidoException;
import empleado.exception.FechaIngresoInvalidaException;
import empleado.exception.PuestoInvalidoException;
import empleado.exception.SalarioInvalidoException;
import empleado.exception.LegajoDuplicadoException;
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(EmpleadoNoEncontradoException.class)
    public ResponseEntity<Void> manejarEmpleadoNoEncontrado(
            EmpleadoNoEncontradoException exception) {

        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .build();
    }
    @ExceptionHandler({
            NombreEmpleadoInvalidoException.class,
            LegajoInvalidoException.class,
            FechaIngresoInvalidaException.class,
            PuestoInvalidoException.class,
            SalarioInvalidoException.class
    })
    public ResponseEntity<Void> manejarErroresDeValidacion(RuntimeException exception) {
        return ResponseEntity.badRequest().build();
    }

    @ExceptionHandler(LegajoDuplicadoException.class)
    public ResponseEntity<Void> manejarLegajoDuplicado(LegajoDuplicadoException exception) {
        return ResponseEntity.status(HttpStatus.CONFLICT).build();
    }
}
