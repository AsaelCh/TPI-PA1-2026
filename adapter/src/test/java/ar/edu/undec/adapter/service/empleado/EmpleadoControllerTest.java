
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


import empleado.exception.EmpleadoNoEncontradoException;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;

import empleado.input.ListarEmpleadosInputPort;
import java.util.List;

import empleado.exception.LegajoDuplicadoException;
import empleado.exception.NombreEmpleadoInvalidoException;

@WebMvcTest(EmpleadoController.class)
class EmpleadoControllerTest {

    @MockBean
    private ListarEmpleadosInputPort listarEmpleadosInputPort;

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

    @Test
    void deberiaRetornar404CuandoElEmpleadoNoExiste() throws Exception {

        when(buscarEmpleadoPorLegajoInputPort.ejecutar("XYZ9999"))
                .thenThrow(new EmpleadoNoEncontradoException(
                        "No existe un empleado con el legajo: XYZ9999"
                ));

        mockMvc.perform(get("/empleados/XYZ9999"))
                .andExpect(status().isNotFound());
    }

    @Test
    void deberiaListarTodosLosEmpleadosYRetornar200() throws Exception {

        Empleado empleado1 = Empleado.crear(
                "Juan Perez",
                "ABC1234",
                LocalDate.of(2024, 1, 10),
                Puesto.ANALISTA,
                new BigDecimal("600000")
        );

        Empleado empleado2 = Empleado.crear(
                "Maria Gomez",
                "DEF5678",
                LocalDate.of(2023, 5, 15),
                Puesto.SUPERVISOR,
                new BigDecimal("900000")
        );

        when(listarEmpleadosInputPort.ejecutar())
                .thenReturn(List.of(empleado1, empleado2));

        mockMvc.perform(get("/empleados"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].nombreCompleto").value("Juan Perez"))
                .andExpect(jsonPath("$[1].nombreCompleto").value("Maria Gomez"));
    }

    @Test
    void deberiaRetornar400CuandoElNombreEsInvalido() throws Exception {

        String json = """
            {
                "nombreCompleto": "",
                "legajo": "ABC1234",
                "fechaIngreso": "2024-01-10",
                "puesto": "ANALISTA",
                "salario": 600000
            }
            """;

        mockMvc.perform(post("/empleados")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isBadRequest());
    }

    @Test
    void deberiaRetornar409CuandoElLegajoEstaDuplicado() throws Exception {

        when(crearEmpleadoInputPort.ejecutar(any(Empleado.class)))
                .thenThrow(new LegajoDuplicadoException(
                        "Ya existe un empleado con ese legajo"
                ));

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
                .andExpect(status().isConflict());
    }
}
