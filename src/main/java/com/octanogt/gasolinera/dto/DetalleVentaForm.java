package com.octanogt.gasolinera.dto;

public class DetalleVentaForm {

    private Long productoId;
    private double cantidad;

    public DetalleVentaForm() { }

    public Long getProductoId() { return productoId; }
    public void setProductoId(Long productoId) { this.productoId = productoId; }
    public double getCantidad() { return cantidad; }
    public void setCantidad(double cantidad) { this.cantidad = cantidad; }
}
