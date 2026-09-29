package com.octanogt.gasolinera.controller;

import com.octanogt.gasolinera.model.Asistencia;
import com.octanogt.gasolinera.model.Trabajador;
import com.octanogt.gasolinera.service.AsistenciaService;
import com.octanogt.gasolinera.service.TrabajadorService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Controller
@RequestMapping("/asistencia")
public class AsistenciaController {

    private final AsistenciaService asistenciaService;
    private final TrabajadorService trabajadorService;

    public AsistenciaController(AsistenciaService asistenciaService, TrabajadorService trabajadorService) {
        this.asistenciaService = asistenciaService;
        this.trabajadorService = trabajadorService;
    }

    @GetMapping
    public String listar(Model model) {
        model.addAttribute("pageTitle", "Registro y control de asistencia");
        model.addAttribute("activePage", "asistencia");
        model.addAttribute("crumbs", List.of("Gestión", "Asistencia"));
        model.addAttribute("registros", asistenciaService.listar());
        model.addAttribute("trabajadores", trabajadorService.listarActivos());
        model.addAttribute("enTurno", asistenciaService.contarPorEstado("En turno"));
        model.addAttribute("presentes", asistenciaService.contarPorEstado("Presente"));
        model.addAttribute("tardanzas", asistenciaService.contarPorEstado("Tardanza"));
        model.addAttribute("ausentes", asistenciaService.contarPorEstado("Ausente"));
        return "asistencia/list";
    }

    @PostMapping("/entrada")
    public String registrarEntrada(@RequestParam Long trabajadorId,
                                    @RequestParam(required = false) String hora,
                                    RedirectAttributes redirectAttributes) {
        Trabajador trabajador = trabajadorService.buscarPorId(trabajadorId).orElse(null);
        if (trabajador == null) {
            redirectAttributes.addFlashAttribute("mensajeError", "El trabajador seleccionado no existe.");
            return "redirect:/asistencia";
        }

        LocalTime horaEntrada = (hora != null && !hora.isBlank()) ? LocalTime.parse(hora) : LocalTime.now();
        String estado = horaEntrada.isAfter(LocalTime.of(8, 0)) ? "Tardanza" : "En turno";

        Asistencia asistencia = new Asistencia(null, trabajador.getId(), trabajador.getNombreCompleto(),
                LocalDate.now(), horaEntrada, null, estado);
        asistenciaService.guardar(asistencia);

        redirectAttributes.addFlashAttribute("mensajeExito",
                "Entrada registrada para " + trabajador.getNombreCompleto() + ".");
        return "redirect:/asistencia";
    }

    @GetMapping("/salida/{id}")
    public String registrarSalida(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        Asistencia registro = asistenciaService.buscarPorId(id).orElse(null);
        // Regla R4: la salida solo puede registrarse si existe una entrada previa
        if (registro == null || registro.getHoraEntrada() == null) {
            redirectAttributes.addFlashAttribute("mensajeError",
                    "No se puede registrar la salida: el trabajador no tiene una entrada registrada.");
            return "redirect:/asistencia";
        }
        registro.setHoraSalida(LocalTime.now());
        if (!"Tardanza".equals(registro.getEstado())) {
            registro.setEstado("Presente");
        }
        redirectAttributes.addFlashAttribute("mensajeExito", "Salida registrada correctamente.");
        return "redirect:/asistencia";
    }
}
