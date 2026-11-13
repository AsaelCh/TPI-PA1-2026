
package ar.edu.undec.adapter.data.empleado.mapper;

import ar.edu.undec.adapter.data.empleado.model.EmpleadoEntity;
import empleado.modelo.Empleado;
import empleado.modelo.Puesto;

public class EmpleadoMapperJpa {

    public EmpleadoEntity toEntity(Empleado empleado) {
        EmpleadoEntity entity = new EmpleadoEntity();

        entity.setId(empleado.getId());
        entity.setNombreCompleto(empleado.getNombreCompleto());
        entity.setLegajo(empleado.getLegajo());
        entity.setFechaIngreso(empleado.getFechaIngreso());
        entity.setPuesto(empleado.getPuesto().name());
        entity.setSalario(empleado.getSalario());

        return entity;
    }

    public Empleado toDomain(EmpleadoEntity entity) {
        return Empleado.reconstruir(
                entity.getId(),
                entity.getNombreCompleto(),
                entity.getLegajo(),
                entity.getFechaIngreso(),
                Puesto.valueOf(entity.getPuesto()),
                entity.getSalario()
        );
    }
}
