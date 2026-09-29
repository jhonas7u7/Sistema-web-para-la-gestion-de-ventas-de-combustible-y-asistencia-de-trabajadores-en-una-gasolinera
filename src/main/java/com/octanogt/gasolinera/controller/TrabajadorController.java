package com.octanogt.gasolinera.controller;

import com.octanogt.gasolinera.model.Trabajador;
import com.octanogt.gasolinera.service.TrabajadorService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/trabajadores")
public class TrabajadorController {

    private final TrabajadorService trabajadorService;

    public TrabajadorController(TrabajadorService trabajadorService) {
        this.trabajadorService = trabajadorService;
    }

    @GetMapping
    public String listar(Model model) {
        model.addAttribute("pageTitle", "Gestión de trabajadores");
        model.addAttribute("activePage", "trabajadores");
        model.addAttribute("crumbs", List.of("Gestión", "Trabajadores"));
        model.addAttribute("trabajadores", trabajadorService.listar());
        return "trabajadores/list";
    }

    @GetMapping("/nuevo")
    public String nuevo(Model model) {
        model.addAttribute("pageTitle", "Nuevo trabajador");
        model.addAttribute("activePage", "trabajadores");
        model.addAttribute("crumbs", List.of("Gestión", "Trabajadores", "Nuevo"));
        model.addAttribute("trabajador", new Trabajador());
        return "trabajadores/form";
    }

    @GetMapping("/editar/{id}")
    public String editar(@PathVariable Long id, Model model, RedirectAttributes redirectAttributes) {
        return trabajadorService.buscarPorId(id)
                .map(trabajador -> {
                    model.addAttribute("pageTitle", "Editar trabajador");
                    model.addAttribute("activePage", "trabajadores");
                    model.addAttribute("crumbs", List.of("Gestión", "Trabajadores", "Editar"));
                    model.addAttribute("trabajador", trabajador);
                    return "trabajadores/form";
                })
                .orElseGet(() -> {
                    redirectAttributes.addFlashAttribute("mensajeError", "El trabajador solicitado no existe.");
                    return "redirect:/trabajadores";
                });
    }

    @PostMapping("/guardar")
    public String guardar(@ModelAttribute Trabajador trabajador, RedirectAttributes redirectAttributes) {
        boolean esNuevo = trabajador.getId() == null;
        trabajadorService.guardar(trabajador);
        redirectAttributes.addFlashAttribute("mensajeExito",
                esNuevo ? "Trabajador registrado correctamente." : "Trabajador actualizado correctamente.");
        return "redirect:/trabajadores";
    }

    @GetMapping("/eliminar/{id}")
    public String eliminar(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        trabajadorService.eliminar(id);
        redirectAttributes.addFlashAttribute("mensajeExito", "Trabajador eliminado correctamente.");
        return "redirect:/trabajadores";
    }
}
