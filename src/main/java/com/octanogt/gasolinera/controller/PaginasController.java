package com.octanogt.gasolinera.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

/** Paginas generales: gestion (hub), contacto, publicidad y metricas (vista informativa). */
@Controller
public class PaginasController {

    @GetMapping("/gestion")
    public String gestion(Model model) {
        model.addAttribute("pageTitle", "Gestión");
        model.addAttribute("activePage", "gestion");
        model.addAttribute("crumbs", List.of("Gestión"));
        return "gestion";
    }

    @GetMapping("/contacto")
    public String contacto(Model model) {
        model.addAttribute("pageTitle", "Contacto");
        model.addAttribute("activePage", "contacto");
        model.addAttribute("crumbs", List.of("Contacto"));
        return "contacto";
    }

    @PostMapping("/contacto")
    public String enviarContacto(@RequestParam String nombre, @RequestParam String correo,
                                  @RequestParam String asunto, @RequestParam String mensaje,
                                  RedirectAttributes redirectAttributes) {
        // Envio simulado: en este avance no se conecta a un servicio de correo real.
        redirectAttributes.addFlashAttribute("mensajeExito",
                "Gracias " + nombre + ", tu mensaje fue recibido (envío simulado).");
        return "redirect:/contacto";
    }

    @GetMapping("/publicidad")
    public String publicidad(Model model) {
        model.addAttribute("pageTitle", "Publicidad");
        model.addAttribute("activePage", "publicidad");
        model.addAttribute("crumbs", List.of("Publicidad"));
        return "publicidad";
    }

    @GetMapping("/metricas")
    public String metricas(Model model) {
        model.addAttribute("pageTitle", "Métricas");
        model.addAttribute("activePage", "metricas");
        model.addAttribute("crumbs", List.of("Análisis", "Métricas"));
        return "metricas";
    }
}
