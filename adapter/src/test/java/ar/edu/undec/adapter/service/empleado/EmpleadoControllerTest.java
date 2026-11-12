
package ar.edu.undec.adapter.service.empleado;

import ar.edu.undec.adapter.service.empleado.controller.EmpleadoController;
import empleado.input.CrearEmpleadoInputPort;
import empleado.modelo.Empleado;
import empleado.modelo.Puesto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import empleado.input.BuscarEmpleadoPorLegajoInputPort;

import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
@WebMvcTest(EmpleadoController.class)
class EmpleadoControllerTest {

    @MockBean
    private BuscarEmpleadoPorLegajoInputPort buscarEmpleadoPorLegajoInputPort;

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CrearEmpleadoInputPort crearEmpleadoInputPort;

    @Test
    void deberiaCrearEmpleadoYRetornar201() throws Exception {

        Empleado empleado = Empleado.crear(
                "Juan Perez",
                "ABC1234",
                LocalDate.of(2024, 1, 10),
                Puesto.ANALISTA,
                new BigDecimal("600000")
        );

        when(crearEmpleadoInputPort.ejecutar(any(Empleado.class)))
                .thenReturn(empleado);

        String json = """
                {
                    "nombreCompleto": "Juan Perez",
                    "legajo": "ABC1234",
                    "fechaIngreso": "2024-01-10",
                    "puesto": "ANALISTA",
                    "salario": 600000
                }
                """;

        mockMvc.perform(post("/empleados")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.nombreCompleto").value("Juan Perez"))
                .andExpect(jsonPath("$.legajo").value("ABC1234"))
                .andExpect(jsonPath("$.puesto").value("ANALISTA"));
    }


    @Test
    void deberiaBuscarEmpleadoPorLegajoYRetornar200() throws Exception {

        Empleado empleado = Empleado.crear(
                "Juan Perez",
                "ABC1234",
                LocalDate.of(2024, 1, 10),
                Puesto.ANALISTA,
                new BigDecimal("600000")
        );

        when(buscarEmpleadoPorLegajoInputPort.ejecutar("ABC1234"))
                .thenReturn(empleado);

        mockMvc.perform(get("/empleados/ABC1234"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nombreCompleto").value("Juan Perez"))
                .andExpect(jsonPath("$.legajo").value("ABC1234"))
                .andExpect(jsonPath("$.puesto").value("ANALISTA"));

        verify(buscarEmpleadoPorLegajoInputPort).ejecutar("ABC1234");
    }
}
