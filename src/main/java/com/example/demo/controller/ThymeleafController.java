package com.example.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import java.util.Arrays;
import java.util.List;

@Controller
public class ThymeleafController {

    // EJEMPLO 1: CONDICIONAL IF - Mostrar mensaje según edad
    @GetMapping("/ejemplo1")
    public String ejemploIf(Model model) {
        // Variable para probar condición
        int edad = 20;

        model.addAttribute("edad", edad);
        return "ejemplo1";
    }

    // EJEMPLO 2: BUCLE FOR - Lista de productos
    @GetMapping("/ejemplo2")
    public String ejemploFor(Model model) {
        // Crear lista de productos manualmente
        List<String> productos = Arrays.asList("Laptop", "Mouse", "Teclado", "Monitor", "Audífonos");

        model.addAttribute("productos", productos);
        return "ejemplo2";
    }

    // EJEMPLO 3: IF + FOR combinados - Lista de estudiantes con calificaciones
    @GetMapping("/ejemplo3")
    public String ejemploIfFor(Model model) {
        // Clase interna para estudiantes (todo en el controlador)
        class Estudiante {
            String nombre;
            double nota;

            Estudiante(String nombre, double nota) {
                this.nombre = nombre;
                this.nota = nota;
            }

            public String getNombre() { return nombre; }
            public double getNota() { return nota; }
        }

        List<Estudiante> estudiantes = Arrays.asList(
                new Estudiante("Ana", 8.5),
                new Estudiante("Carlos", 5.0),
                new Estudiante("María", 9.0),
                new Estudiante("Juan", 4.5),
                new Estudiante("Laura", 7.0)
        );

        model.addAttribute("estudiantes", estudiantes);
        return "ejemplo3";
    }
}