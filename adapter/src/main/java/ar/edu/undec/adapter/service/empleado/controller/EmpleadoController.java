
package ar.edu.undec.adapter.service.empleado.controller;

import ar.edu.undec.adapter.service.empleado.mapper.EmpleadoMapper;
import ar.edu.undec.adapter.service.empleado.model.EmpleadoRequest;
import ar.edu.undec.adapter.service.empleado.model.EmpleadoResponse;
import empleado.input.CrearEmpleadoInputPort;
import empleado.modelo.Empleado;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/empleados")
public class EmpleadoController {

    private final CrearEmpleadoInputPort crearEmpleadoInputPort;
    private final EmpleadoMapper empleadoMapper;

    public EmpleadoController(CrearEmpleadoInputPort crearEmpleadoInputPort) {
        this.crearEmpleadoInputPort = crearEmpleadoInputPort;
        this.empleadoMapper = new EmpleadoMapper();
    }

    @PostMapping
    public ResponseEntity<EmpleadoResponse> crear(
            @RequestBody EmpleadoRequest request) {

        Empleado empleado = empleadoMapper.toDomain(request);

        Empleado empleadoCreado =
                crearEmpleadoInputPort.ejecutar(empleado);

        EmpleadoResponse response =
                empleadoMapper.toResponse(empleadoCreado);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
}
