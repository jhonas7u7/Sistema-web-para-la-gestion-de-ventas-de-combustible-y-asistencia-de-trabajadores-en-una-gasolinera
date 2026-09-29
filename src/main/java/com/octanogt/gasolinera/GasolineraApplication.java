package com.octanogt.gasolinera;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Sistema Web para la Gestion de Ventas de Combustible y Asistencia
 * de Trabajadores de una Gasolinera.
 *
 * Avance 2: proyecto funcional con Spring Boot + Thymeleaf.
 * Los datos se administran en memoria (sin base de datos, sin JPA).
 */
@SpringBootApplication
public class GasolineraApplication {

    public static void main(String[] args) {
        SpringApplication.run(GasolineraApplication.class, args);
    }
}
