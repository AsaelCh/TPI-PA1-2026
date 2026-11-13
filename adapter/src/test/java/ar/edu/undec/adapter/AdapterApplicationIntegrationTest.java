package ar.edu.undec.adapter;

import empleado.input.BuscarEmpleadoPorLegajoInputPort;
import empleado.input.CrearEmpleadoInputPort;
import empleado.input.ListarEmpleadosInputPort;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
class AdapterApplicationIntegrationTest {

    @Autowired
    private CrearEmpleadoInputPort crearEmpleadoInputPort;

    @Autowired
    private BuscarEmpleadoPorLegajoInputPort buscarEmpleadoPorLegajoInputPort;

    @Autowired
    private ListarEmpleadosInputPort listarEmpleadosInputPort;

    @Test
    void deberiaIniciarLaAplicacionYConfigurarLosCasosDeUso() {
        assertNotNull(crearEmpleadoInputPort);
        assertNotNull(buscarEmpleadoPorLegajoInputPort);
        assertNotNull(listarEmpleadosInputPort);
    }
}