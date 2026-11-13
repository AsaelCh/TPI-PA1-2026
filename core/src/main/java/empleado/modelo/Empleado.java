package empleado.modelo;

import empleado.exception.NombreEmpleadoInvalidoException;
import empleado.exception.LegajoInvalidoException;
import empleado.exception.FechaIngresoInvalidaException;
import empleado.exception.PuestoInvalidoException;
import empleado.exception.SalarioInvalidoException;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

public class Empleado {

    private final UUID id;
    private final String nombreCompleto;
    private final String legajo;
    private final LocalDate fechaIngreso;
    private final Puesto puesto;
    private final BigDecimal salario;

    private Empleado(UUID id, String nombreCompleto, String legajo,
                     LocalDate fechaIngreso, Puesto puesto, BigDecimal salario) {

        this.id = id;
        this.nombreCompleto = nombreCompleto;
        this.legajo = legajo;
        this.fechaIngreso = fechaIngreso;
        this.puesto = puesto;
        this.salario = salario;
    }

    public static Empleado crear(String nombreCompleto, String legajo,
                                 LocalDate fechaIngreso, Puesto puesto,
                                 BigDecimal salario) {

        validarNombre(nombreCompleto);
        validarLegajo(legajo);
        validarFechaIngreso(fechaIngreso);
        validarPuesto(puesto);
        validarSalario(salario, puesto);

        UUID id = UUID.randomUUID();

        return new Empleado(id, nombreCompleto.trim(), legajo,
                fechaIngreso, puesto, salario);
    }
    public static Empleado reconstruir(UUID id, String nombreCompleto,
                                       String legajo, LocalDate fechaIngreso,
                                       Puesto puesto, BigDecimal salario) {

        if (id == null) {
            throw new IllegalArgumentException("El ID no puede ser nulo");
        }

        validarNombre(nombreCompleto);
        validarLegajo(legajo);
        validarFechaIngreso(fechaIngreso);
        validarPuesto(puesto);
        validarSalario(salario, puesto);

        return new Empleado(id, nombreCompleto.trim(), legajo,
                fechaIngreso, puesto, salario);
    }

    private static void validarNombre(String nombreCompleto) {
        if (nombreCompleto == null || nombreCompleto.isBlank()) {
            throw new NombreEmpleadoInvalidoException(
                    "El nombre completo es obligatorio");
        }
    }

    private static void validarLegajo(String legajo) {
        if (legajo == null || !legajo.matches("[A-Z]{3}[0-9]{4}")) {
            throw new LegajoInvalidoException(
                    "El legajo debe tener 3 letras mayusculas y 4 numeros");
        }
    }

    private static void validarFechaIngreso(LocalDate fechaIngreso) {
        if (fechaIngreso == null || fechaIngreso.isAfter(LocalDate.now())) {
            throw new FechaIngresoInvalidaException(
                    "La fecha de ingreso no puede ser nula ni futura");
        }
    }

    private static void validarPuesto(Puesto puesto) {
        if (puesto == null) {
            throw new PuestoInvalidoException(
                    "El puesto es obligatorio");
        }
    }

    private static void validarSalario(BigDecimal salario, Puesto puesto) {
        if (salario == null || salario.compareTo(BigDecimal.ZERO) <= 0) {
            throw new SalarioInvalidoException(
                    "El salario debe ser mayor que cero");
        }

        BigDecimal minimo;

        switch (puesto) {
            case ANALISTA:
                minimo = new BigDecimal("500000");
                break;
            case SUPERVISOR:
                minimo = new BigDecimal("800000");
                break;
            case GERENTE:
                minimo = new BigDecimal("1200000");
                break;
            default:
                throw new PuestoInvalidoException("Puesto invalido");
        }

        if (salario.compareTo(minimo) < 0) {
            throw new SalarioInvalidoException(
                    "El salario no alcanza el minimo para el puesto");
        }
    }

    public UUID getId() {
        return id;
    }

    public String getNombreCompleto() {
        return nombreCompleto;
    }

    public String getLegajo() {
        return legajo;
    }

    public LocalDate getFechaIngreso() {
        return fechaIngreso;
    }

    public Puesto getPuesto() {
        return puesto;
    }

    public BigDecimal getSalario() {
        return salario;
    }
}