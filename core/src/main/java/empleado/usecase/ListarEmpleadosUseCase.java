package empleado.usecase;

import empleado.modelo.Empleado;
import empleado.output.EmpleadoOutputPort;

import java.util.List;

public class ListarEmpleadosUseCase {

    private final EmpleadoOutputPort empleadoOutputPort;

    public ListarEmpleadosUseCase(EmpleadoOutputPort empleadoOutputPort) {
        this.empleadoOutputPort = empleadoOutputPort;
    }

    public List<Empleado> ejecutar() {
        return empleadoOutputPort.listarTodos();
    }
}