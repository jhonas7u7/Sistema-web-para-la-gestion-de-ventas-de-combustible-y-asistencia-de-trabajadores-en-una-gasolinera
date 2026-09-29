package com.octanogt.gasolinera.controller;

import com.octanogt.gasolinera.model.Usuario;
import com.octanogt.gasolinera.service.UsuarioService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/usuarios")
public class UsuarioController {

    private final UsuarioService usuarioService;

    public UsuarioController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    @GetMapping
    public String listar(Model model) {
        model.addAttribute("pageTitle", "Gestión de usuarios");
        model.addAttribute("activePage", "usuarios");
        model.addAttribute("crumbs", List.of("Gestión", "Usuarios"));
        model.addAttribute("usuarios", usuarioService.listar());
        return "usuarios/list";
    }

    @GetMapping("/nuevo")
    public String nuevo(Model model) {
        model.addAttribute("pageTitle", "Nuevo usuario");
        model.addAttribute("activePage", "usuarios");
        model.addAttribute("crumbs", List.of("Gestión", "Usuarios", "Nuevo"));
        model.addAttribute("usuario", new Usuario());
        return "usuarios/form";
    }

    @GetMapping("/editar/{id}")
    public String editar(@PathVariable Long id, Model model, RedirectAttributes redirectAttributes) {
        return usuarioService.buscarPorId(id)
                .map(usuario -> {
                    model.addAttribute("pageTitle", "Editar usuario");
                    model.addAttribute("activePage", "usuarios");
                    model.addAttribute("crumbs", List.of("Gestión", "Usuarios", "Editar"));
                    model.addAttribute("usuario", usuario);
                    return "usuarios/form";
                })
                .orElseGet(() -> {
                    redirectAttributes.addFlashAttribute("mensajeError", "El usuario solicitado no existe.");
                    return "redirect:/usuarios";
                });
    }

    @PostMapping("/guardar")
    public String guardar(@ModelAttribute Usuario usuario, RedirectAttributes redirectAttributes) {
        boolean esNuevo = usuario.getId() == null;
        usuarioService.guardar(usuario);
        redirectAttributes.addFlashAttribute("mensajeExito",
                esNuevo ? "Usuario registrado correctamente." : "Usuario actualizado correctamente.");
        return "redirect:/usuarios";
    }

    @GetMapping("/eliminar/{id}")
    public String eliminar(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        usuarioService.eliminar(id);
        redirectAttributes.addFlashAttribute("mensajeExito", "Usuario eliminado correctamente.");
        return "redirect:/usuarios";
    }
}
