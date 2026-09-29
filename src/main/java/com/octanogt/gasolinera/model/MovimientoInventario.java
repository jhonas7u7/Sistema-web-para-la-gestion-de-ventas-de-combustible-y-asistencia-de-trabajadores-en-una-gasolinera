package com.octanogt.gasolinera.model;

import java.time.LocalDateTime;

public class MovimientoInventario {

    private Long id;
    private LocalDateTime fecha;
    private Long productoId;
    private String productoNombre;
    private String tipo; // Entrada, Salida, Ajuste
    private double cantidad;
    private String responsable;
    private String observacion;

    public MovimientoInventario() { }

    public MovimientoInventario(Long id, LocalDateTime fecha, Long productoId, String productoNombre,
                                 String tipo, double cantidad, String responsable, String observacion) {
        this.id = id;
        this.fecha = fecha;
        this.productoId = productoId;
        this.productoNombre = productoNombre;
        this.tipo = tipo;
        this.cantidad = cantidad;
        this.responsable = responsable;
        this.observacion = observacion;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public LocalDateTime getFecha() { return fecha; }
    public void setFecha(LocalDateTime fecha) { this.fecha = fecha; }
    public Long getProductoId() { return productoId; }
    public void setProductoId(Long productoId) { this.productoId = productoId; }
    public String getProductoNombre() { return productoNombre; }
    public void setProductoNombre(String productoNombre) { this.productoNombre = productoNombre; }
    public String getTipo() { return tipo; }
    public void setTipo(String tipo) { this.tipo = tipo; }
    public double getCantidad() { return cantidad; }
    public void setCantidad(double cantidad) { this.cantidad = cantidad; }
    public String getResponsable() { return responsable; }
    public void setResponsable(String responsable) { this.responsable = responsable; }
    public String getObservacion() { return observacion; }
    public void setObservacion(String observacion) { this.observacion = observacion; }
}
