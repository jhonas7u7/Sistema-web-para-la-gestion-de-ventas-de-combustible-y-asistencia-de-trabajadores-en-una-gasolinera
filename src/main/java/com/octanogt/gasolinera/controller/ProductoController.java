package com.octanogt.gasolinera.controller;

import com.octanogt.gasolinera.model.Producto;
import com.octanogt.gasolinera.service.CategoriaService;
import com.octanogt.gasolinera.service.ProductoService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/productos")
public class ProductoController {

    private final ProductoService productoService;
    private final CategoriaService categoriaService;

    public ProductoController(ProductoService productoService, CategoriaService categoriaService) {
        this.productoService = productoService;
        this.categoriaService = categoriaService;
    }

    @GetMapping
    public String listar(Model model) {
        model.addAttribute("pageTitle", "Gestión de productos y combustibles");
        model.addAttribute("activePage", "productos");
        model.addAttribute("crumbs", List.of("Gestión", "Productos"));
        model.addAttribute("productos", productoService.listar());
        return "productos/list";
    }

    @GetMapping("/nuevo")
    public String nuevo(Model model) {
        model.addAttribute("pageTitle", "Nuevo producto");
        model.addAttribute("activePage", "productos");
        model.addAttribute("crumbs", List.of("Gestión", "Productos", "Nuevo"));
        model.addAttribute("producto", new Producto());
        model.addAttribute("categorias", categoriaService.listarActivas());
        return "productos/form";
    }

    @GetMapping("/editar/{id}")
    public String editar(@PathVariable Long id, Model model, RedirectAttributes redirectAttributes) {
        return productoService.buscarPorId(id)
                .map(producto -> {
                    model.addAttribute("pageTitle", "Editar producto");
                    model.addAttribute("activePage", "productos");
                    model.addAttribute("crumbs", List.of("Gestión", "Productos", "Editar"));
                    model.addAttribute("producto", producto);
                    model.addAttribute("categorias", categoriaService.listarActivas());
                    return "productos/form";
                })
                .orElseGet(() -> {
                    redirectAttributes.addFlashAttribute("mensajeError", "El producto solicitado no existe.");
                    return "redirect:/productos";
                });
    }

    @PostMapping("/guardar")
    public String guardar(@ModelAttribute Producto producto, RedirectAttributes redirectAttributes) {
        boolean esNuevo = producto.getId() == null;
        productoService.guardar(producto);
        redirectAttributes.addFlashAttribute("mensajeExito",
                esNuevo ? "Producto registrado correctamente." : "Producto actualizado correctamente.");
        return "redirect:/productos";
    }

    @GetMapping("/eliminar/{id}")
    public String eliminar(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        productoService.eliminar(id);
        redirectAttributes.addFlashAttribute("mensajeExito", "Producto eliminado correctamente.");
        return "redirect:/productos";
    }
}
