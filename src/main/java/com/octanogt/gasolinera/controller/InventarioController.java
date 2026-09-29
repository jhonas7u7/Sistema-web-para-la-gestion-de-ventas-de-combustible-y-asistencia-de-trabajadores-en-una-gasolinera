package com.octanogt.gasolinera.controller;

import com.octanogt.gasolinera.service.ProductoService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class InventarioController {

    private final ProductoService productoService;

    public InventarioController(ProductoService productoService) {
        this.productoService = productoService;
    }

    @GetMapping("/inventario")
    public String listar(Model model) {
        model.addAttribute("pageTitle", "Control de inventario");
        model.addAttribute("activePage", "inventario");
        model.addAttribute("crumbs", List.of("Gestión", "Inventario"));
        model.addAttribute("productos", productoService.listar());
        long alertas = productoService.listar().stream()
                .filter(p -> !"Stock normal".equals(p.getEstadoInventario()))
                .count();
        model.addAttribute("cantidadAlertas", alertas);
        return "inventario";
    }
}
