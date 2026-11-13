package empleado.usecase;

import empleado.input.BuscarEmpleadoPorLegajoInputPort;
import empleado.exception.EmpleadoNoEncontradoException;
import empleado.modelo.Empleado;
import empleado.output.EmpleadoOutputPort;

import java.util.Optional;

public class BuscarEmpleadoPorLegajoUseCase implements BuscarEmpleadoPorLegajoInputPort {

    private final EmpleadoOutputPort empleadoOutputPort;

    public BuscarEmpleadoPorLegajoUseCase(EmpleadoOutputPort empleadoOutputPort) {
        this.empleadoOutputPort = empleadoOutputPort;
    }
    @Override
    public Empleado ejecutar(String legajo) {

        Optional<Empleado> empleado =
                empleadoOutputPort.buscarPorLegajo(legajo);

        if (empleado.isEmpty()) {
            throw new EmpleadoNoEncontradoException(
                    "No existe un empleado con el legajo: " + legajo
            );
        }

        return empleado.get();
    }
}