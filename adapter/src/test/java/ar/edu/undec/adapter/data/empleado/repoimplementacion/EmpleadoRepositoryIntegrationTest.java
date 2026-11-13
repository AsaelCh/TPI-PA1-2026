
package ar.edu.undec.adapter.data.empleado.repoimplementacion;

import ar.edu.undec.adapter.data.empleado.model.EmpleadoEntity;
import empleado.modelo.Empleado;
import empleado.modelo.Puesto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@Import(EmpleadoRepository.class)
class EmpleadoRepositoryIntegrationTest {

    @Autowired
    private EmpleadoRepository empleadoRepository;

    @Test
    void deberiaGuardarEmpleadoEnLaBaseDeDatos() {
        Empleado empleado = Empleado.crear(
                "Juan Perez",
                "ABC1234",
                LocalDate.of(2024, 1, 10),
                Puesto.ANALISTA,
                new BigDecimal("600000")
        );

        Empleado guardado = empleadoRepository.guardar(empleado);

        assertNotNull(guardado);
        assertEquals(empleado.getId(), guardado.getId());
        assertEquals("Juan Perez", guardado.getNombreCompleto());
        assertEquals("ABC1234", guardado.getLegajo());
    }

    @Test
    void deberiaBuscarEmpleadoPorLegajo() {
        Empleado empleado = Empleado.crear(
                "Maria Lopez",
                "XYZ1234",
                LocalDate.of(2023, 5, 15),
                Puesto.ANALISTA,
                new BigDecimal("600000")
        );

        empleadoRepository.guardar(empleado);

        var encontrado = empleadoRepository.buscarPorLegajo("XYZ1234");

        assertTrue(encontrado.isPresent());
        assertEquals("Maria Lopez", encontrado.get().getNombreCompleto());
        assertEquals(empleado.getId(), encontrado.get().getId());
    }
    @Test
    void deberiaListarTodosLosEmpleados() {
        Empleado empleado1 = Empleado.crear(
                "Juan Perez",
                "ABC1234",
                LocalDate.of(2024, 1, 10),
                Puesto.ANALISTA,
                new BigDecimal("600000")
        );

        Empleado empleado2 = Empleado.crear(
                "Maria Lopez",
                "XYZ1234",
                LocalDate.of(2023, 5, 15),
                Puesto.SUPERVISOR,
                new BigDecimal("900000")
        );

        empleadoRepository.guardar(empleado1);
        empleadoRepository.guardar(empleado2);

        var empleados = empleadoRepository.listarTodos();

        assertEquals(2, empleados.size());
        assertTrue(empleados.stream()
                .anyMatch(e -> e.getLegajo().equals("ABC1234")));
        assertTrue(empleados.stream()
                .anyMatch(e -> e.getLegajo().equals("XYZ1234")));
    }

    @Test
    void deberiaIndicarSiExisteUnEmpleadoPorLegajo() {
        Empleado empleado = Empleado.crear(
                "Carlos Gomez",
                "DEF1234",
                LocalDate.of(2024, 3, 20),
                Puesto.ANALISTA,
                new BigDecimal("600000")
        );

        empleadoRepository.guardar(empleado);

        assertTrue(empleadoRepository.existePorLegajo("DEF1234"));
        assertFalse(empleadoRepository.existePorLegajo("ZZZ9999"));
    }

}
