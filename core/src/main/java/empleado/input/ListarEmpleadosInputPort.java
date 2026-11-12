package empleado.input;

import empleado.modelo.Empleado;
import java.util.List;

public interface ListarEmpleadosInputPort {

    List<Empleado> ejecutar();
}