package empleado.usecase;
import empleado.input.ListarEmpleadosInputPort;
import empleado.modelo.Empleado;
import empleado.output.EmpleadoOutputPort;

import java.util.List;

public class ListarEmpleadosUseCase implements ListarEmpleadosInputPort {

    private final EmpleadoOutputPort empleadoOutputPort;

    public ListarEmpleadosUseCase(EmpleadoOutputPort empleadoOutputPort) {
        this.empleadoOutputPort = empleadoOutputPort;
    }

    @Override
    public List<Empleado> ejecutar() {
        return empleadoOutputPort.listarTodos();
    }
}