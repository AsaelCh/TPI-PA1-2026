package empleado.usecase;

import empleado.exception.EmpleadoNoEncontradoException;
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
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class BuscarEmpleadoPorLegajoUseCaseTest {

    @Mock
    private EmpleadoOutputPort empleadoOutputPort;

    @InjectMocks
    private BuscarEmpleadoPorLegajoUseCase buscarEmpleadoPorLegajoUseCase;

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
    void debeEncontrarEmpleadoPorLegajo() {
        Empleado empleado = crearEmpleadoValido();

        when(empleadoOutputPort.buscarPorLegajo("ABC1234"))
                .thenReturn(Optional.of(empleado));

        Empleado resultado =
                buscarEmpleadoPorLegajoUseCase.ejecutar("ABC1234");

        assertNotNull(resultado);
        assertEquals("ABC1234", resultado.getLegajo());
    }

    @Test
    void debeLanzarExcepcionSiEmpleadoNoExiste() {

        when(empleadoOutputPort.buscarPorLegajo("XYZ9999"))
                .thenReturn(Optional.empty());

        assertThrows(
                EmpleadoNoEncontradoException.class,
                () -> buscarEmpleadoPorLegajoUseCase.ejecutar("XYZ9999")
        );
    }

    @Test
    void debeConsultarRepositorioConElLegajoSolicitado() {
        Empleado empleado = crearEmpleadoValido();

        when(empleadoOutputPort.buscarPorLegajo("ABC1234"))
                .thenReturn(Optional.of(empleado));

        buscarEmpleadoPorLegajoUseCase.ejecutar("ABC1234");

        verify(empleadoOutputPort).buscarPorLegajo("ABC1234");
    }
}