
package ar.edu.undec.adapter.service.empleado.beanconfig;

import empleado.input.CrearEmpleadoInputPort;
import empleado.input.BuscarEmpleadoPorLegajoInputPort;
import empleado.input.ListarEmpleadosInputPort;
import empleado.output.EmpleadoOutputPort;
import empleado.usecase.CrearEmpleadoUseCase;
import empleado.usecase.BuscarEmpleadoPorLegajoUseCase;
import empleado.usecase.ListarEmpleadosUseCase;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class EmpleadoBeanConfig {

    @Bean
    public CrearEmpleadoInputPort crearEmpleadoUseCase(
            EmpleadoOutputPort empleadoOutputPort) {
        return new CrearEmpleadoUseCase(empleadoOutputPort);
    }

    @Bean
    public BuscarEmpleadoPorLegajoInputPort buscarEmpleadoUseCase(
            EmpleadoOutputPort empleadoOutputPort) {
        return new BuscarEmpleadoPorLegajoUseCase(empleadoOutputPort);
    }

    @Bean
    public ListarEmpleadosInputPort listarEmpleadosUseCase(
            EmpleadoOutputPort empleadoOutputPort) {
        return new ListarEmpleadosUseCase(empleadoOutputPort);
    }
}
