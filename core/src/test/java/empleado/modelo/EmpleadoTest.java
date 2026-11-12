package empleado.modelo;

import empleado.exception.NombreEmpleadoInvalidoException;
import empleado.exception.LegajoInvalidoException;
import empleado.exception.FechaIngresoInvalidaException;
import empleado.exception.PuestoInvalidoException;
import empleado.exception.SalarioInvalidoException;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class EmpleadoTest {

    // CREACION VALIDA

    @Test
    void debeCrearUnAnalistaConDatosValidos() {
        Empleado empleado = Empleado.crear(
                "Juan Perez",
                "ABC1234",
                LocalDate.now().minusDays(1),
                Puesto.ANALISTA,
                new BigDecimal("500000")
        );

        assertNotNull(empleado);
        assertEquals("Juan Perez", empleado.getNombreCompleto());
        assertEquals("ABC1234", empleado.getLegajo());
        assertEquals(Puesto.ANALISTA, empleado.getPuesto());
    }

    @Test
    void debeCrearUnSupervisorConDatosValidos() {
        Empleado empleado = Empleado.crear(
                "Maria Lopez",
                "DEF1234",
                LocalDate.now().minusDays(1),
                Puesto.SUPERVISOR,
                new BigDecimal("800000")
        );

        assertNotNull(empleado);
        assertEquals(Puesto.SUPERVISOR, empleado.getPuesto());
    }

    @Test
    void debeCrearUnGerenteConDatosValidos() {
        Empleado empleado = Empleado.crear(
                "Pedro Gomez",
                "GHI1234",
                LocalDate.now().minusDays(1),
                Puesto.GERENTE,
                new BigDecimal("1200000")
        );

        assertNotNull(empleado);
        assertEquals(Puesto.GERENTE, empleado.getPuesto());
    }

    @Test
    void debeGenerarUnIdentificadorUUIDAutomaticamente() {
        Empleado empleado = crearEmpleadoValido();

        assertNotNull(empleado.getId());
        assertDoesNotThrow(() -> UUID.fromString(
                empleado.getId().toString()
        ));
    }

    // VALIDACIONES DEL NOMBRE

    @Test
    void noDebeCrearEmpleadoConNombreNulo() {
        assertThrows(NombreEmpleadoInvalidoException.class, () ->
                Empleado.crear(
                        null, "ABC1234",
                        LocalDate.now().minusDays(1),
                        Puesto.ANALISTA,
                        new BigDecimal("500000")
                )
        );
    }

    @Test
    void noDebeCrearEmpleadoConNombreVacio() {
        assertThrows(NombreEmpleadoInvalidoException.class, () ->
                Empleado.crear(
                        "   ", "ABC1234",
                        LocalDate.now().minusDays(1),
                        Puesto.ANALISTA,
                        new BigDecimal("500000")
                )
        );
    }

    // VALIDACIONES DEL LEGAJO

    @Test
    void noDebeCrearEmpleadoConLegajoNulo() {
        assertThrows(LegajoInvalidoException.class, () ->
                Empleado.crear(
                        "Juan Perez", null,
                        LocalDate.now().minusDays(1),
                        Puesto.ANALISTA,
                        new BigDecimal("500000")
                )
        );
    }

    @Test
    void noDebeCrearEmpleadoConLegajoDeFormatoIncorrecto() {
        assertThrows(LegajoInvalidoException.class, () ->
                Empleado.crear(
                        "Juan Perez", "abc123",
                        LocalDate.now().minusDays(1),
                        Puesto.ANALISTA,
                        new BigDecimal("500000")
                )
        );
    }

    // VALIDACIONES DE FECHA

    @Test
    void noDebeCrearEmpleadoConFechaDeIngresoNula() {
        assertThrows(FechaIngresoInvalidaException.class, () ->
                Empleado.crear(
                        "Juan Perez", "ABC1234",
                        null,
                        Puesto.ANALISTA,
                        new BigDecimal("500000")
                )
        );
    }

    @Test
    void noDebeCrearEmpleadoConFechaDeIngresoFutura() {
        assertThrows(FechaIngresoInvalidaException.class, () ->
                Empleado.crear(
                        "Juan Perez", "ABC1234",
                        LocalDate.now().plusDays(1),
                        Puesto.ANALISTA,
                        new BigDecimal("500000")
                )
        );
    }

    // VALIDACIONES DEL PUESTO

    @Test
    void noDebeCrearEmpleadoConPuestoNulo() {
        assertThrows(PuestoInvalidoException.class, () ->
                Empleado.crear(
                        "Juan Perez", "ABC1234",
                        LocalDate.now().minusDays(1),
                        null,
                        new BigDecimal("500000")
                )
        );
    }

    // VALIDACIONES DEL SALARIO

    @Test
    void noDebeCrearEmpleadoConSalarioNulo() {
        assertThrows(SalarioInvalidoException.class, () ->
                Empleado.crear(
                        "Juan Perez", "ABC1234",
                        LocalDate.now().minusDays(1),
                        Puesto.ANALISTA,
                        null
                )
        );
    }

    @Test
    void noDebeCrearEmpleadoConSalarioCero() {
        assertThrows(SalarioInvalidoException.class, () ->
                Empleado.crear(
                        "Juan Perez", "ABC1234",
                        LocalDate.now().minusDays(1),
                        Puesto.ANALISTA,
                        BigDecimal.ZERO
                )
        );
    }

    @Test
    void noDebeCrearEmpleadoConSalarioNegativo() {
        assertThrows(SalarioInvalidoException.class, () ->
                Empleado.crear(
                        "Juan Perez", "ABC1234",
                        LocalDate.now().minusDays(1),
                        Puesto.ANALISTA,
                        new BigDecimal("-100000")
                )
        );
    }

    @Test
    void noDebeCrearAnalistaConSalarioInferiorAlMinimo() {
        assertThrows(SalarioInvalidoException.class, () ->
                Empleado.crear(
                        "Juan Perez", "ABC1234",
                        LocalDate.now().minusDays(1),
                        Puesto.ANALISTA,
                        new BigDecimal("499999")
                )
        );
    }

    @Test
    void noDebeCrearSupervisorConSalarioInferiorAlMinimo() {
        assertThrows(SalarioInvalidoException.class, () ->
                Empleado.crear(
                        "Maria Lopez", "DEF1234",
                        LocalDate.now().minusDays(1),
                        Puesto.SUPERVISOR,
                        new BigDecimal("799999")
                )
        );
    }

    @Test
    void noDebeCrearGerenteConSalarioInferiorAlMinimo() {
        assertThrows(SalarioInvalidoException.class, () ->
                Empleado.crear(
                        "Pedro Gomez", "GHI1234",
                        LocalDate.now().minusDays(1),
                        Puesto.GERENTE,
                        new BigDecimal("1199999")
                )
        );
    }

    @Test
    void debeAceptarSalarioMinimoDeAnalista() {
        Empleado empleado = Empleado.crear(
                "Juan Perez", "ABC1234",
                LocalDate.now().minusDays(1),
                Puesto.ANALISTA,
                new BigDecimal("500000")
        );

        assertEquals(0, empleado.getSalario().compareTo(
                new BigDecimal("500000")
        ));
    }

    @Test
    void debeAceptarSalarioMinimoDeSupervisor() {
        Empleado empleado = Empleado.crear(
                "Maria Lopez", "DEF1234",
                LocalDate.now().minusDays(1),
                Puesto.SUPERVISOR,
                new BigDecimal("800000")
        );

        assertEquals(0, empleado.getSalario().compareTo(
                new BigDecimal("800000")
        ));
    }

    @Test
    void debeAceptarSalarioMinimoDeGerente() {
        Empleado empleado = Empleado.crear(
                "Pedro Gomez", "GHI1234",
                LocalDate.now().minusDays(1),
                Puesto.GERENTE,
                new BigDecimal("1200000")
        );

        assertEquals(0, empleado.getSalario().compareTo(
                new BigDecimal("1200000")
        ));
    }

    // METODO AUXILIAR

    private Empleado crearEmpleadoValido() {
        return Empleado.crear(
                "Juan Perez",
                "ABC1234",
                LocalDate.now().minusDays(1),
                Puesto.ANALISTA,
                new BigDecimal("500000")
        );
    }
}
