package empleado.usecase;

import empleado.input.CrearEmpleadoInputPort;
import empleado.exception.LegajoDuplicadoException;
import empleado.modelo.Empleado;
import empleado.output.EmpleadoOutputPort;

public class CrearEmpleadoUseCase implements CrearEmpleadoInputPort {

    private final EmpleadoOutputPort empleadoOutputPort;

    public CrearEmpleadoUseCase(EmpleadoOutputPort empleadoOutputPort) {
        this.empleadoOutputPort = empleadoOutputPort;
    }

    @Override
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