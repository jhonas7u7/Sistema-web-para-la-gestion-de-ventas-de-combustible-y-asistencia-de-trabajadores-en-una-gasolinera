package com.octanogt.gasolinera.controller;

import com.octanogt.gasolinera.service.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

/**
 * Reportes basicos generados a partir de los datos en memoria.
 * No incluye metricas (fuera del alcance de este avance).
 */
@Controller
public class ReporteController {

    private final VentaService ventaService;
    private final ProductoService productoService;
    private final AsistenciaService asistenciaService;
    private final TrabajadorService trabajadorService;

    public ReporteController(VentaService ventaService, ProductoService productoService,
                              AsistenciaService asistenciaService, TrabajadorService trabajadorService) {
        this.ventaService = ventaService;
        this.productoService = productoService;
        this.asistenciaService = asistenciaService;
        this.trabajadorService = trabajadorService;
    }

    @GetMapping("/reportes")
    public String reportes(Model model) {
        model.addAttribute("pageTitle", "Reportes");
        model.addAttribute("activePage", "reportes");
        model.addAttribute("crumbs", List.of("Análisis", "Reportes"));

        double totalVentas = ventaService.listar().stream()
                .filter(v -> "Pagada".equals(v.getEstado()))
                .mapToDouble(v -> v.getTotal()).sum();

        model.addAttribute("totalVentas", totalVentas);
        model.addAttribute("cantidadVentas", ventaService.listar().size());
        model.addAttribute("cantidadProductos", productoService.listar().size());
        model.addAttribute("cantidadMovimientosStockBajo", productoService.listar().stream()
                .filter(p -> !"Stock normal".equals(p.getEstadoInventario())).count());
        model.addAttribute("cantidadTrabajadores", trabajadorService.listar().size());
        model.addAttribute("presentesHoy", asistenciaService.contarPorEstado("Presente")
                + asistenciaService.contarPorEstado("En turno"));
        return "reportes";
    }
}
