package com.gasolinera.app.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class FragmentController {

    @GetMapping({"/","/inicio"})
    public String mostrarInicio() {
        return "inicio";
    }

    @GetMapping("/gestion")
    public String mostrarGestion() {
        return "gestion";
    }

    @GetMapping("/usuarios")
    public String mostrarUsuarios() {
        return "usuarios";
    }

    @GetMapping("/asistencia")
    public String mostrarAsistencia(){
        return "asistencia";
    }

    @GetMapping("/categorias")
    public String mostrarCategorias(){
        return "categorias";
    }

    @GetMapping("/clientes")
    public String mostrarClientes (){
        return "clientes";
    }

    @GetMapping("/contacto")
    public String mostrarContacto (){
        return "contacto";
    }

    @GetMapping("/historial-ventas")
    public String mostrarHistorialVentas(){
        return "historial-ventas";
    }

    @GetMapping("/inventario")
    public String mostrarInventario(){
        return "inventario";
    }

    @GetMapping("/metricas")
    public String mostrarMetricas(){
        return "metricas";
    }

    @GetMapping("/movimientos")
    public String mostrarMovimientos(){
        return "movimientos";
    }

    @GetMapping("/login")
    public String mostrarLogin(){
        return "login";
    }

    @GetMapping("/productos")
    public String mostrarProductos(){
        return "productos";
    }

    @GetMapping("/publicidad")
    public String mostrarPublicidad(){
        return "publicidad";
    }

    @GetMapping("/reportes")
    public String mostrarReportes(){
        return "reportes";
    }

    @GetMapping("/trabajadores")
    public String mostrarTrabajadores(){
        return "trabajadores";
    }

    @GetMapping("/ventas")
    public String mostrarVentas(){
        return "ventas";
    }
}