package com.octanogt.gasolinera.controller;

import com.octanogt.gasolinera.service.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class DashboardController {

    private final VentaService ventaService;
    private final ProductoService productoService;
    private final AsistenciaService asistenciaService;
    private final TrabajadorService trabajadorService;

    public DashboardController(VentaService ventaService, ProductoService productoService,
                                AsistenciaService asistenciaService, TrabajadorService trabajadorService) {
        this.ventaService = ventaService;
        this.productoService = productoService;
        this.asistenciaService = asistenciaService;
        this.trabajadorService = trabajadorService;
    }

    @GetMapping("/")
    public String inicio(Model model) {
        double ventasHoy = ventaService.listar().stream()
                .filter(v -> "Pagada".equals(v.getEstado()))
                .mapToDouble(v -> v.getTotal())
                .sum();

        long cantidadVentas = ventaService.listar().stream()
                .filter(v -> "Pagada".equals(v.getEstado()))
                .count();

        long productosStockBajo = productoService.listar().stream()
                .filter(p -> !"Stock normal".equals(p.getEstadoInventario()))
                .count();

        long enTurno = asistenciaService.contarPorEstado("En turno");

        model.addAttribute("pageTitle", "Inicio");
        model.addAttribute("activePage", "index");
        model.addAttribute("crumbs", List.of("Dashboard", "Inicio"));
        model.addAttribute("ventasHoy", ventasHoy);
        model.addAttribute("cantidadVentas", cantidadVentas);
        model.addAttribute("productosStockBajo", productosStockBajo);
        model.addAttribute("trabajadoresEnTurno", enTurno);
        model.addAttribute("totalTrabajadoresActivos", trabajadorService.listarActivos().size());
        model.addAttribute("ultimasVentas", ventaService.listar().stream().limit(5).toList());
        model.addAttribute("productosBajoStock", productoService.listar().stream()
                .filter(p -> !"Stock normal".equals(p.getEstadoInventario()))
                .limit(5).toList());
        return "index";
    }
}
