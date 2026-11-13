package ar.edu.undec.adapter.service.empleado.mapper;

import ar.edu.undec.adapter.service.empleado.model.EmpleadoRequest;
import ar.edu.undec.adapter.service.empleado.model.EmpleadoResponse;
import empleado.exception.PuestoInvalidoException;
import empleado.modelo.Empleado;
import empleado.modelo.Puesto;

public class EmpleadoMapper {

    public Empleado toDomain(EmpleadoRequest request) {
        return Empleado.crear(
                request.getNombreCompleto(),
                request.getLegajo(),
                request.getFechaIngreso(),
                toPuesto(request.getPuesto()),
                request.getSalario()
        );
    }

    private Puesto toPuesto(String puesto) {
        if (puesto == null) {
            throw new PuestoInvalidoException("El puesto es obligatorio");
        }

        try {
            return Puesto.valueOf(puesto);
        } catch (IllegalArgumentException exception) {
            throw new PuestoInvalidoException("El puesto no es válido");
        }
    }

    public EmpleadoResponse toResponse(Empleado empleado) {
        EmpleadoResponse response = new EmpleadoResponse();

        response.setId(empleado.getId());
        response.setNombreCompleto(empleado.getNombreCompleto());
        response.setLegajo(empleado.getLegajo());
        response.setFechaIngreso(empleado.getFechaIngreso());
        response.setPuesto(empleado.getPuesto().name());
        response.setSalario(empleado.getSalario());

        return response;
    }
}
