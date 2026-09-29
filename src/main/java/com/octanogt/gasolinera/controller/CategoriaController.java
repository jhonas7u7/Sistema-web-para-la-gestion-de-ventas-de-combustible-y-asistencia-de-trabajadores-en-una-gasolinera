package com.octanogt.gasolinera.controller;

import com.octanogt.gasolinera.model.Categoria;
import com.octanogt.gasolinera.service.CategoriaService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/categorias")
public class CategoriaController {

    private final CategoriaService categoriaService;

    public CategoriaController(CategoriaService categoriaService) {
        this.categoriaService = categoriaService;
    }

    @GetMapping
    public String listar(Model model) {
        model.addAttribute("pageTitle", "Gestión de categorías");
        model.addAttribute("activePage", "categorias");
        model.addAttribute("crumbs", List.of("Gestión", "Categorías"));
        model.addAttribute("categorias", categoriaService.listar());
        return "categorias/list";
    }

    @GetMapping("/nuevo")
    public String nuevo(Model model) {
        model.addAttribute("pageTitle", "Nueva categoría");
        model.addAttribute("activePage", "categorias");
        model.addAttribute("crumbs", List.of("Gestión", "Categorías", "Nueva"));
        model.addAttribute("categoria", new Categoria());
        return "categorias/form";
    }

    @GetMapping("/editar/{id}")
    public String editar(@PathVariable Long id, Model model, RedirectAttributes redirectAttributes) {
        return categoriaService.buscarPorId(id)
                .map(categoria -> {
                    model.addAttribute("pageTitle", "Editar categoría");
                    model.addAttribute("activePage", "categorias");
                    model.addAttribute("crumbs", List.of("Gestión", "Categorías", "Editar"));
                    model.addAttribute("categoria", categoria);
                    return "categorias/form";
                })
                .orElseGet(() -> {
                    redirectAttributes.addFlashAttribute("mensajeError", "La categoría solicitada no existe.");
                    return "redirect:/categorias";
                });
    }

    @PostMapping("/guardar")
    public String guardar(@ModelAttribute Categoria categoria, RedirectAttributes redirectAttributes) {
        boolean esNuevo = categoria.getId() == null;
        categoriaService.guardar(categoria);
        redirectAttributes.addFlashAttribute("mensajeExito",
                esNuevo ? "Categoría registrada correctamente." : "Categoría actualizada correctamente.");
        return "redirect:/categorias";
    }

    @GetMapping("/desactivar/{id}")
    public String desactivar(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        categoriaService.desactivar(id);
        redirectAttributes.addFlashAttribute("mensajeExito",
                "Categoría desactivada correctamente (no se elimina para conservar el historial).");
        return "redirect:/categorias";
    }
}
