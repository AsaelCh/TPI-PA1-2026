package empleado.usecase;

import empleado.exception.EmpleadoNoEncontradoException;
import empleado.modelo.Empleado;
import empleado.output.EmpleadoOutputPort;

import java.util.Optional;

public class BuscarEmpleadoPorLegajoUseCase {

    private final EmpleadoOutputPort empleadoOutputPort;

    public BuscarEmpleadoPorLegajoUseCase(EmpleadoOutputPort empleadoOutputPort) {
        this.empleadoOutputPort = empleadoOutputPort;
    }

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