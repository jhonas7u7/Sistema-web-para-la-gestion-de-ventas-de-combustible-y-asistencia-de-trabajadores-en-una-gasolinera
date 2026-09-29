package com.octanogt.gasolinera.controller;

import com.octanogt.gasolinera.model.Cliente;
import com.octanogt.gasolinera.service.ClienteService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/clientes")
public class ClienteController {

    private final ClienteService clienteService;

    public ClienteController(ClienteService clienteService) {
        this.clienteService = clienteService;
    }

    @GetMapping
    public String listar(Model model) {
        model.addAttribute("pageTitle", "Gestión de clientes");
        model.addAttribute("activePage", "clientes");
        model.addAttribute("crumbs", List.of("Gestión", "Clientes"));
        model.addAttribute("clientes", clienteService.listar());
        return "clientes/list";
    }

    @GetMapping("/nuevo")
    public String nuevo(Model model) {
        model.addAttribute("pageTitle", "Nuevo cliente");
        model.addAttribute("activePage", "clientes");
        model.addAttribute("crumbs", List.of("Gestión", "Clientes", "Nuevo"));
        model.addAttribute("cliente", new Cliente());
        return "clientes/form";
    }

    @GetMapping("/editar/{id}")
    public String editar(@PathVariable Long id, Model model, RedirectAttributes redirectAttributes) {
        return clienteService.buscarPorId(id)
                .map(cliente -> {
                    model.addAttribute("pageTitle", "Editar cliente");
                    model.addAttribute("activePage", "clientes");
                    model.addAttribute("crumbs", List.of("Gestión", "Clientes", "Editar"));
                    model.addAttribute("cliente", cliente);
                    return "clientes/form";
                })
                .orElseGet(() -> {
                    redirectAttributes.addFlashAttribute("mensajeError", "El cliente solicitado no existe.");
                    return "redirect:/clientes";
                });
    }

    @PostMapping("/guardar")
    public String guardar(@ModelAttribute Cliente cliente, RedirectAttributes redirectAttributes) {
        boolean esNuevo = cliente.getId() == null;
        clienteService.guardar(cliente);
        redirectAttributes.addFlashAttribute("mensajeExito",
                esNuevo ? "Cliente registrado correctamente." : "Cliente actualizado correctamente.");
        return "redirect:/clientes";
    }

    @GetMapping("/eliminar/{id}")
    public String eliminar(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        clienteService.eliminar(id);
        redirectAttributes.addFlashAttribute("mensajeExito", "Cliente eliminado correctamente.");
        return "redirect:/clientes";
    }
}
