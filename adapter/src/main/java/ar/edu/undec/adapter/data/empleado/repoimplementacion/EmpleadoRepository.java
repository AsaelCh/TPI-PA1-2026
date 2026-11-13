
package ar.edu.undec.adapter.data.empleado.repoimplementacion;

import ar.edu.undec.adapter.data.empleado.crud.EmpleadoCrudRepository;
import ar.edu.undec.adapter.data.empleado.mapper.EmpleadoMapperJpa;
import ar.edu.undec.adapter.data.empleado.model.EmpleadoEntity;
import empleado.modelo.Empleado;
import empleado.output.EmpleadoOutputPort;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class EmpleadoRepository implements EmpleadoOutputPort {

    private final EmpleadoCrudRepository empleadoCrudRepository;
    private final EmpleadoMapperJpa empleadoMapper;

    public EmpleadoRepository(EmpleadoCrudRepository empleadoCrudRepository) {
        this.empleadoCrudRepository = empleadoCrudRepository;
        this.empleadoMapper = new EmpleadoMapperJpa();
    }

    @Override
    public boolean existePorLegajo(String legajo) {
        return empleadoCrudRepository.existsByLegajo(legajo);
    }

    @Override
    public Empleado guardar(Empleado empleado) {
        EmpleadoEntity entity = empleadoMapper.toEntity(empleado);
        EmpleadoEntity guardado = empleadoCrudRepository.save(entity);
        return empleadoMapper.toDomain(guardado);
    }

    @Override
    public Optional<Empleado> buscarPorLegajo(String legajo) {
        return empleadoCrudRepository.findByLegajo(legajo)
                .map(empleadoMapper::toDomain);
    }

    @Override
    public List<Empleado> listarTodos() {
        return empleadoCrudRepository.findAll()
                .stream()
                .map(empleadoMapper::toDomain)
                .toList();
    }
}
