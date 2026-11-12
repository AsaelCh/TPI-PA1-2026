package empleado.usecase;

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
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ListarEmpleadosUseCaseTest {

    @Mock
    private EmpleadoOutputPort empleadoOutputPort;

    @InjectMocks
    private ListarEmpleadosUseCase listarEmpleadosUseCase;

    private Empleado crearEmpleadoValido(
            String nombre, String legajo) {

        return Empleado.crear(
                nombre,
                legajo,
                LocalDate.now(),
                Puesto.ANALISTA,
                new BigDecimal("500000")
        );
    }

    @Test
    void debeListarTodosLosEmpleados() {
        Empleado empleado1 =
                crearEmpleadoValido("Juan Perez", "ABC1234");

        Empleado empleado2 =
                crearEmpleadoValido("Maria Lopez", "XYZ5678");

        when(empleadoOutputPort.listarTodos())
                .thenReturn(Arrays.asList(empleado1, empleado2));

        List<Empleado> resultado = listarEmpleadosUseCase.ejecutar();

        assertEquals(2, resultado.size());
        assertEquals("ABC1234", resultado.get(0).getLegajo());
        assertEquals("XYZ5678", resultado.get(1).getLegajo());
    }

    @Test
    void debeRetornarListaVaciaSiNoHayEmpleados() {

        when(empleadoOutputPort.listarTodos())
                .thenReturn(Collections.emptyList());

        List<Empleado> resultado = listarEmpleadosUseCase.ejecutar();

        assertNotNull(resultado);
        assertTrue(resultado.isEmpty());
    }

    @Test
    void debeConsultarRepositorioParaListarEmpleados() {

        when(empleadoOutputPort.listarTodos())
                .thenReturn(Collections.emptyList());

        listarEmpleadosUseCase.ejecutar();

        verify(empleadoOutputPort).listarTodos();
    }
}