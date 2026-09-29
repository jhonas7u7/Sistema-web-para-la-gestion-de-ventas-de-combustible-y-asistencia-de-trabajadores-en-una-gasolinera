package com.octanogt.gasolinera.dto;

import java.util.ArrayList;
import java.util.List;

public class VentaForm {

    public static final int MAX_ITEMS = 5;

    private Long clienteId;
    private Long trabajadorId;
    private String metodoPago = "Efectivo";
    private List<DetalleVentaForm> items = new ArrayList<>();

    public VentaForm() {
        for (int i = 0; i < MAX_ITEMS; i++) {
            items.add(new DetalleVentaForm());
        }
    }

    public Long getClienteId() { return clienteId; }
    public void setClienteId(Long clienteId) { this.clienteId = clienteId; }
    public Long getTrabajadorId() { return trabajadorId; }
    public void setTrabajadorId(Long trabajadorId) { this.trabajadorId = trabajadorId; }
    public String getMetodoPago() { return metodoPago; }
    public void setMetodoPago(String metodoPago) { this.metodoPago = metodoPago; }
    public List<DetalleVentaForm> getItems() { return items; }
    public void setItems(List<DetalleVentaForm> items) { this.items = items; }
}
