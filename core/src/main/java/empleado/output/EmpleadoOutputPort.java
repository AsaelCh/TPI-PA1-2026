package empleado.output;

import empleado.modelo.Empleado;

public interface EmpleadoOutputPort {

    boolean existePorLegajo(String legajo);

    Empleado guardar(Empleado empleado);
}