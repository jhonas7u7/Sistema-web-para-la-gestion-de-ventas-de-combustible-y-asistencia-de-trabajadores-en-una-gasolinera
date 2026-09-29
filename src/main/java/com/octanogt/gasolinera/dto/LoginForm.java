package com.octanogt.gasolinera.dto;

public class LoginForm {

    private String usuario;
    private String contrasena;
    private boolean recordarme;

    public String getUsuario() { return usuario; }
    public void setUsuario(String usuario) { this.usuario = usuario; }
    public String getContrasena() { return contrasena; }
    public void setContrasena(String contrasena) { this.contrasena = contrasena; }
    public boolean isRecordarme() { return recordarme; }
    public void setRecordarme(boolean recordarme) { this.recordarme = recordarme; }
}
