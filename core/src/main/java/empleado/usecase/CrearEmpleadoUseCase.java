package empleado.usecase;

import empleado.exception.LegajoDuplicadoException;
import empleado.modelo.Empleado;
import empleado.output.EmpleadoOutputPort;

public class CrearEmpleadoUseCase {

    private final EmpleadoOutputPort empleadoOutputPort;

    public CrearEmpleadoUseCase(EmpleadoOutputPort empleadoOutputPort) {
        this.empleadoOutputPort = empleadoOutputPort;
    }

    public Empleado ejecutar(Empleado empleado) {

        boolean existe = empleadoOutputPort.existePorLegajo(
                empleado.getLegajo()
        );

        if (existe) {
            throw new LegajoDuplicadoException(
                    "Ya existe un empleado con ese legajo"
            );
        }

        return empleadoOutputPort.guardar(empleado);
    }
}