package com.octanogt.gasolinera.controller;

import com.octanogt.gasolinera.model.MovimientoInventario;
import com.octanogt.gasolinera.service.MovimientoInventarioService;
import com.octanogt.gasolinera.service.ProductoService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/movimientos")
public class MovimientoController {

    private final MovimientoInventarioService movimientoService;
    private final ProductoService productoService;

    public MovimientoController(MovimientoInventarioService movimientoService, ProductoService productoService) {
        this.movimientoService = movimientoService;
        this.productoService = productoService;
    }

    @GetMapping
    public String listar(Model model) {
        model.addAttribute("pageTitle", "Movimientos de inventario");
        model.addAttribute("activePage", "movimientos");
        model.addAttribute("crumbs", List.of("Gestión", "Movimientos"));
        model.addAttribute("movimientos", movimientoService.listar());
        return "movimientos/list";
    }

    @GetMapping("/nuevo")
    public String nuevo(Model model) {
        model.addAttribute("pageTitle", "Nuevo movimiento de inventario");
        model.addAttribute("activePage", "movimientos");
        model.addAttribute("crumbs", List.of("Gestión", "Movimientos", "Nuevo"));
        model.addAttribute("movimiento", new MovimientoInventario());
        model.addAttribute("productos", productoService.listarActivos());
        return "movimientos/form";
    }

    @PostMapping("/guardar")
    public String guardar(@RequestParam Long productoId, @RequestParam String tipo,
                           @RequestParam double cantidad, @RequestParam String responsable,
                           @RequestParam(required = false) String observacion,
                           RedirectAttributes redirectAttributes) {

        String productoNombre = productoService.buscarPorId(productoId)
                .map(p -> p.getNombre())
                .orElse("Producto no encontrado");

        MovimientoInventario movimiento = new MovimientoInventario();
        movimiento.setProductoId(productoId);
        movimiento.setProductoNombre(productoNombre);
        movimiento.setTipo(tipo);
        movimiento.setCantidad(cantidad);
        movimiento.setResponsable(responsable);
        movimiento.setObservacion(observacion);

        movimientoService.registrar(movimiento, true);

        redirectAttributes.addFlashAttribute("mensajeExito",
                "Movimiento de inventario registrado. El stock de " + productoNombre + " fue actualizado.");
        return "redirect:/movimientos";
    }
}
