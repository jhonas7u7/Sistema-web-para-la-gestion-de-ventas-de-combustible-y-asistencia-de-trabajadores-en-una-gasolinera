package com.example.demo.controller;

import com.example.demo.model.Persona;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Controller
public class PersonaController {

    // Simulamos un array (lista) para guardar los datos
    private List<Persona> listaPersonas = new ArrayList<>();

    // Mostrar formulario
    @GetMapping("/")
    public String mostrarFormulario(Model model) {
        model.addAttribute("persona", new Persona());
        return "formulario";
    }

    // Procesar el formulario y mostrar lista
    @PostMapping("/guardar")
    public String guardarPersona(@ModelAttribute Persona persona, Model model) {
        // Agregar nueva persona a la lista
        listaPersonas.add(persona);

        // Enviar la lista completa a la vista
        model.addAttribute("personas", listaPersonas);
        model.addAttribute("personaActual", persona);

        return "lista";
    }
}