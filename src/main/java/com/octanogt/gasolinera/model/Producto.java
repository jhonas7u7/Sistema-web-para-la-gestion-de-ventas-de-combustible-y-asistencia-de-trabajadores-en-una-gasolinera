package com.octanogt.gasolinera.model;

public class Producto {

    private Long id;
    private String codigo;
    private String nombre;
    private String categoria;
    private double precio;
    private double stock;
    private String unidadMedida; // gal, unid.
    private double stockMinimo;
    private String estado; // Activo, Inactivo

    public Producto() { }

    public Producto(Long id, String codigo, String nombre, String categoria, double precio,
                     double stock, String unidadMedida, double stockMinimo, String estado) {
        this.id = id;
        this.codigo = codigo;
        this.nombre = nombre;
        this.categoria = categoria;
        this.precio = precio;
        this.stock = stock;
        this.unidadMedida = unidadMedida;
        this.stockMinimo = stockMinimo;
        this.estado = estado;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getCodigo() { return codigo; }
    public void setCodigo(String codigo) { this.codigo = codigo; }
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    public String getCategoria() { return categoria; }
    public void setCategoria(String categoria) { this.categoria = categoria; }
    public double getPrecio() { return precio; }
    public void setPrecio(double precio) { this.precio = precio; }
    public double getStock() { return stock; }
    public void setStock(double stock) { this.stock = stock; }
    public String getUnidadMedida() { return unidadMedida; }
    public void setUnidadMedida(String unidadMedida) { this.unidadMedida = unidadMedida; }
    public double getStockMinimo() { return stockMinimo; }
    public void setStockMinimo(double stockMinimo) { this.stockMinimo = stockMinimo; }
    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }

    /** Estado visual del inventario segun el stock disponible frente al minimo. */
    public String getEstadoInventario() {
        if (stock <= stockMinimo * 0.5) return "Stock crítico";
        if (stock <= stockMinimo) return "Stock bajo";
        return "Stock normal";
    }
}
