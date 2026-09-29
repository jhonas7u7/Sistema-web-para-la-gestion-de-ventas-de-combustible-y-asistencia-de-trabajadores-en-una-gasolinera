package com.octanogt.gasolinera.controller;

import com.octanogt.gasolinera.dto.DetalleVentaForm;
import com.octanogt.gasolinera.dto.VentaForm;
import com.octanogt.gasolinera.model.Cliente;
import com.octanogt.gasolinera.model.DetalleVenta;
import com.octanogt.gasolinera.model.Producto;
import com.octanogt.gasolinera.model.Trabajador;
import com.octanogt.gasolinera.model.Venta;
import com.octanogt.gasolinera.service.ClienteService;
import com.octanogt.gasolinera.service.ProductoService;
import com.octanogt.gasolinera.service.TrabajadorService;
import com.octanogt.gasolinera.service.VentaService;
import org.springframework.beans.propertyeditors.CustomNumberEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/ventas")
public class VentaController {

    private final VentaService ventaService;
    private final ProductoService productoService;
    private final ClienteService clienteService;
    private final TrabajadorService trabajadorService;

    public VentaController(VentaService ventaService, ProductoService productoService,
                            ClienteService clienteService, TrabajadorService trabajadorService) {
        this.ventaService = ventaService;
        this.productoService = productoService;
        this.clienteService = clienteService;
        this.trabajadorService = trabajadorService;
    }

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        binder.registerCustomEditor(Long.class, new CustomNumberEditor(Long.class, true));
    }

    @GetMapping("/nueva")
    public String nueva(Model model) {
        model.addAttribute("pageTitle", "Registro de ventas");
        model.addAttribute("activePage", "ventas");
        model.addAttribute("crumbs", List.of("Ventas", "Nueva venta"));
        model.addAttribute("ventaForm", new VentaForm());
        model.addAttribute("clientes", clienteService.listarActivos());
        model.addAttribute("trabajadores", trabajadorService.listarActivos());
        model.addAttribute("productos", productoService.listarActivos());
        return "ventas/form";
    }

    @PostMapping("/guardar")
    public String guardar(@ModelAttribute VentaForm ventaForm, RedirectAttributes redirectAttributes) {

        List<DetalleVenta> detalles = new ArrayList<>();
        for (DetalleVentaForm item : ventaForm.getItems()) {
            if (item.getProductoId() != null && item.getCantidad() > 0) {
                Producto producto = productoService.buscarPorId(item.getProductoId()).orElse(null);
                if (producto != null) {
                    detalles.add(new DetalleVenta(producto.getId(), producto.getNombre(),
                            item.getCantidad(), producto.getPrecio()));
                }
            }
        }

        if (detalles.isEmpty()) {
            redirectAttributes.addFlashAttribute("mensajeError",
                    "Debes seleccionar al menos un producto con una cantidad válida.");
            return "redirect:/ventas/nueva";
        }

        for (DetalleVenta d : detalles) {
            Producto p = productoService.buscarPorId(d.getProductoId()).orElse(null);
            if (p != null && p.getStock() < d.getCantidad()) {
                redirectAttributes.addFlashAttribute("mensajeError",
                        "Stock insuficiente de " + p.getNombre() + ": disponible "
                                + String.format("%.2f", p.getStock()) + " " + p.getUnidadMedida() + ".");
                return "redirect:/ventas/nueva";
            }
        }

        Cliente cliente = clienteService.buscarPorId(ventaForm.getClienteId()).orElse(null);
        Trabajador trabajador = trabajadorService.buscarPorId(ventaForm.getTrabajadorId()).orElse(null);

        if (trabajador == null) {
            redirectAttributes.addFlashAttribute("mensajeError",
                    "Debes seleccionar un trabajador responsable para registrar la venta.");
            return "redirect:/ventas/nueva";
        }

        Venta venta = new Venta();
        venta.setClienteId(ventaForm.getClienteId());
        venta.setClienteNombre(cliente != null ? cliente.getNombre() : "Cliente genérico");
        venta.setTrabajadorId(ventaForm.getTrabajadorId());
        venta.setTrabajadorNombre(trabajador != null ? trabajador.getNombreCompleto() : "-");
        venta.setMetodoPago(ventaForm.getMetodoPago());
        venta.setDetalles(detalles);

        Venta registrada = ventaService.registrar(venta);

        redirectAttributes.addFlashAttribute("mensajeExito",
                "Venta " + registrada.getNumero() + " registrada correctamente por un total de S/ "
                        + String.format("%.2f", registrada.getTotal()) + ".");
        return "redirect:/ventas/historial";
    }

    @GetMapping("/historial")
    public String historial(Model model) {
        model.addAttribute("pageTitle", "Historial de ventas");
        model.addAttribute("activePage", "historial");
        model.addAttribute("crumbs", List.of("Ventas", "Historial"));
        model.addAttribute("ventas", ventaService.listar());
        return "ventas/historial";
    }

    @GetMapping("/detalle/{id}")
    public String detalle(@PathVariable Long id, Model model, RedirectAttributes redirectAttributes) {
        return ventaService.buscarPorId(id)
                .map(venta -> {
                    model.addAttribute("pageTitle", "Detalle de venta " + venta.getNumero());
                    model.addAttribute("activePage", "historial");
                    model.addAttribute("crumbs", List.of("Ventas", "Historial", "Detalle"));
                    model.addAttribute("venta", venta);
                    return "ventas/detalle";
                })
                .orElseGet(() -> {
                    redirectAttributes.addFlashAttribute("mensajeError", "La venta solicitada no existe.");
                    return "redirect:/ventas/historial";
                });
    }

    @GetMapping("/anular/{id}")
    public String anular(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        ventaService.anular(id);
        redirectAttributes.addFlashAttribute("mensajeExito", "Venta anulada correctamente.");
        return "redirect:/ventas/historial";
    }
}
