
package ar.edu.undec.adapter.service.empleado.controller;

import ar.edu.undec.adapter.service.empleado.mapper.EmpleadoMapper;
import ar.edu.undec.adapter.service.empleado.model.EmpleadoRequest;
import ar.edu.undec.adapter.service.empleado.model.EmpleadoResponse;
import empleado.input.CrearEmpleadoInputPort;
import empleado.modelo.Empleado;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import empleado.input.BuscarEmpleadoPorLegajoInputPort;

@RestController
@RequestMapping("/empleados")
public class EmpleadoController {

    private final CrearEmpleadoInputPort crearEmpleadoInputPort;
    private final EmpleadoMapper empleadoMapper;
    private final BuscarEmpleadoPorLegajoInputPort buscarEmpleadoPorLegajoInputPort;

    public EmpleadoController(
            CrearEmpleadoInputPort crearEmpleadoInputPort,
            BuscarEmpleadoPorLegajoInputPort buscarEmpleadoPorLegajoInputPort) {

        this.crearEmpleadoInputPort = crearEmpleadoInputPort;
        this.buscarEmpleadoPorLegajoInputPort = buscarEmpleadoPorLegajoInputPort;
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

    @GetMapping("/{legajo}")
    public ResponseEntity<EmpleadoResponse> buscarPorLegajo(
            @PathVariable("legajo") String legajo) {

        Empleado empleado =
                buscarEmpleadoPorLegajoInputPort.ejecutar(legajo);

        EmpleadoResponse response =
                empleadoMapper.toResponse(empleado);

        return ResponseEntity.ok(response);
    }
}
