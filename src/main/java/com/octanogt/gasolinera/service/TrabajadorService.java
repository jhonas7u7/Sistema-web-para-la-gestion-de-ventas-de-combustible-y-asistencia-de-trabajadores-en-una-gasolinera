package com.octanogt.gasolinera.service;

import com.octanogt.gasolinera.model.Trabajador;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;

@Service
public class TrabajadorService {

    private final List<Trabajador> trabajadores = new ArrayList<>();
    private final AtomicLong secuencia = new AtomicLong(0);

    @PostConstruct
    public void cargarDatosDeEjemplo() {
        guardar(new Trabajador(null, "T-001", "Renzo Quispe Alarcon", "Despachador", "987 654 321", LocalDate.of(2024, 3, 12), "Activo"));
        guardar(new Trabajador(null, "T-002", "Lucia Fernandez Rios", "Cajero", "987 111 222", LocalDate.of(2024, 6, 5), "Activo"));
        guardar(new Trabajador(null, "T-003", "Jorge Salas Medina", "Encargado de inventario", "987 333 444", LocalDate.of(2023, 1, 20), "Activo"));
        guardar(new Trabajador(null, "T-004", "Milagros Paredes Luna", "Cajero", "987 555 666", LocalDate.of(2025, 9, 14), "Activo"));
        guardar(new Trabajador(null, "T-005", "Diego Huaman Torres", "Despachador", "987 777 888", LocalDate.of(2022, 2, 2), "Inactivo"));
    }

    public List<Trabajador> listar() {
        return trabajadores;
    }

    public List<Trabajador> listarActivos() {
        return trabajadores.stream().filter(t -> "Activo".equals(t.getEstado())).toList();
    }

    public Optional<Trabajador> buscarPorId(Long id) {
        return trabajadores.stream().filter(t -> t.getId().equals(id)).findFirst();
    }

    public Trabajador guardar(Trabajador trabajador) {
        if (trabajador.getId() == null) {
            trabajador.setId(secuencia.incrementAndGet());
            trabajadores.add(trabajador);
            return trabajador;
        }
        for (int i = 0; i < trabajadores.size(); i++) {
            if (trabajadores.get(i).getId().equals(trabajador.getId())) {
                trabajadores.set(i, trabajador);
                return trabajador;
            }
        }
        trabajadores.add(trabajador);
        return trabajador;
    }

    public void eliminar(Long id) {
        trabajadores.removeIf(t -> t.getId().equals(id));
    }
}
