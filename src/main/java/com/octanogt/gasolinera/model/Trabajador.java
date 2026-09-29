package com.octanogt.gasolinera.model;

import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

public class Trabajador {

    private Long id;
    private String codigo;
    private String nombreCompleto;
    private String cargo;        // Despachador, Cajero, Encargado de inventario, Administrador
    private String telefono;
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate fechaIngreso;
    private String estado;       // Activo, Inactivo

    public Trabajador() { }

    public Trabajador(Long id, String codigo, String nombreCompleto, String cargo,
                       String telefono, LocalDate fechaIngreso, String estado) {
        this.id = id;
        this.codigo = codigo;
        this.nombreCompleto = nombreCompleto;
        this.cargo = cargo;
        this.telefono = telefono;
        this.fechaIngreso = fechaIngreso;
        this.estado = estado;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getCodigo() { return codigo; }
    public void setCodigo(String codigo) { this.codigo = codigo; }
    public String getNombreCompleto() { return nombreCompleto; }
    public void setNombreCompleto(String nombreCompleto) { this.nombreCompleto = nombreCompleto; }
    public String getCargo() { return cargo; }
    public void setCargo(String cargo) { this.cargo = cargo; }
    public String getTelefono() { return telefono; }
    public void setTelefono(String telefono) { this.telefono = telefono; }
    public LocalDate getFechaIngreso() { return fechaIngreso; }
    public void setFechaIngreso(LocalDate fechaIngreso) { this.fechaIngreso = fechaIngreso; }
    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }
}
