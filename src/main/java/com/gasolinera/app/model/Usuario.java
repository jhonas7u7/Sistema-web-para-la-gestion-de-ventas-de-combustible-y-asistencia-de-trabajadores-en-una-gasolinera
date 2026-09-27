package com.gasolinera.app.model;

public class Usuario {
    private Long id;
    private String nombre;
    private String username;
    private String email;
    private String rol;
    private String estado;

    public Usuario() {}

    public Usuario(Long id, String nombre, String username, String email, String rol, String estado) {
        this.id = id;
        this.nombre = nombre;
        this.username = username;
        this.email = email;
        this.rol = rol;
        this.estado = estado;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public String getRol() { return rol; }
    public void setRol(String rol) { this.rol = rol; }
    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }
}