package empleado.input;

import empleado.modelo.Empleado;

public interface BuscarEmpleadoPorLegajoInputPort {

    Empleado ejecutar(String legajo);
}