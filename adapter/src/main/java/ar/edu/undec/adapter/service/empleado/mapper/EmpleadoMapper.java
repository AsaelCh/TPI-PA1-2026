package ar.edu.undec.adapter.service.empleado.mapper;

import ar.edu.undec.adapter.service.empleado.model.EmpleadoRequest;
import ar.edu.undec.adapter.service.empleado.model.EmpleadoResponse;
import empleado.modelo.Empleado;
import empleado.modelo.Puesto;

public class EmpleadoMapper {

    public Empleado toDomain(EmpleadoRequest request) {
        return Empleado.crear(
                request.getNombreCompleto(),
                request.getLegajo(),
                request.getFechaIngreso(),
                Puesto.valueOf(request.getPuesto()),
                request.getSalario()
        );
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
