package com.octanogt.gasolinera.model;

public class Usuario {

    private Long id;
    private String nombreCompleto;
    private String usuario;
    private String correo;
    private String rol;      // Administrador, Encargado de ventas, Trabajador, Encargado de inventario
    private String estado;   // Activo, Inactivo

    public Usuario() { }

    public Usuario(Long id, String nombreCompleto, String usuario, String correo, String rol, String estado) {
        this.id = id;
        this.nombreCompleto = nombreCompleto;
        this.usuario = usuario;
        this.correo = correo;
        this.rol = rol;
        this.estado = estado;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getNombreCompleto() { return nombreCompleto; }
    public void setNombreCompleto(String nombreCompleto) { this.nombreCompleto = nombreCompleto; }
    public String getUsuario() { return usuario; }
    public void setUsuario(String usuario) { this.usuario = usuario; }
    public String getCorreo() { return correo; }
    public void setCorreo(String correo) { this.correo = correo; }
    public String getRol() { return rol; }
    public void setRol(String rol) { this.rol = rol; }
    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }
}
