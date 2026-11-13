
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
}
