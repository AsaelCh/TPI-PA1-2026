package empleado.usecase;

import empleado.exception.LegajoDuplicadoException;
import empleado.modelo.Empleado;
import empleado.modelo.Puesto;
import empleado.output.EmpleadoOutputPort;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CrearEmpleadoUseCaseTest {

    @Mock
    private EmpleadoOutputPort empleadoOutputPort;

    @InjectMocks
    private CrearEmpleadoUseCase crearEmpleadoUseCase;

    private Empleado crearEmpleadoValido() {
        return Empleado.crear(
                "Juan Perez",
                "ABC1234",
                LocalDate.now(),
                Puesto.ANALISTA,
                new BigDecimal("500000")
        );
    }

    @Test
    void debeCrearEmpleadoCorrectamente() {
        Empleado empleado = crearEmpleadoValido();

        when(empleadoOutputPort.existePorLegajo("ABC1234"))
                .thenReturn(false);

        when(empleadoOutputPort.guardar(empleado))
                .thenReturn(empleado);

        Empleado resultado = crearEmpleadoUseCase.ejecutar(empleado);

        assertNotNull(resultado);
        assertEquals("ABC1234", resultado.getLegajo());
        verify(empleadoOutputPort).guardar(empleado);
    }

    @Test
    void debeConsultarSiElLegajoExiste() {
        Empleado empleado = crearEmpleadoValido();

        when(empleadoOutputPort.existePorLegajo("ABC1234"))
                .thenReturn(false);

        when(empleadoOutputPort.guardar(empleado))
                .thenReturn(empleado);

        crearEmpleadoUseCase.ejecutar(empleado);

        verify(empleadoOutputPort).existePorLegajo("ABC1234");
    }

    @Test
    void noDebeCrearEmpleadoConLegajoDuplicado() {
        Empleado empleado = crearEmpleadoValido();

        when(empleadoOutputPort.existePorLegajo("ABC1234"))
                .thenReturn(true);

        assertThrows(
                LegajoDuplicadoException.class,
                () -> crearEmpleadoUseCase.ejecutar(empleado)
        );

        verify(empleadoOutputPort, never()).guardar(empleado);
    }

    @Test
    void debePropagarErrorSiFallaElGuardado() {
        Empleado empleado = crearEmpleadoValido();

        when(empleadoOutputPort.existePorLegajo("ABC1234"))
                .thenReturn(false);

        when(empleadoOutputPort.guardar(empleado))
                .thenThrow(new IllegalStateException("Error de persistencia"));

        assertThrows(
                IllegalStateException.class,
                () -> crearEmpleadoUseCase.ejecutar(empleado)
        );
    }
}