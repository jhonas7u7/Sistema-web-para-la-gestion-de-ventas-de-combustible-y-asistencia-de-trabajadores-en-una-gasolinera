package com.octanogt.gasolinera.service;

import com.octanogt.gasolinera.model.Asistencia;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;

@Service
public class AsistenciaService {

    private final List<Asistencia> registros = new ArrayList<>();
    private final AtomicLong secuencia = new AtomicLong(0);

    @PostConstruct
    public void cargarDatosDeEjemplo() {
        LocalDate hoy = LocalDate.now();
        guardar(new Asistencia(null, 1L, "Renzo Quispe Alarcon", hoy, LocalTime.of(6, 58), null, "En turno"));
        guardar(new Asistencia(null, 2L, "Lucia Fernandez Rios", hoy, LocalTime.of(7, 2), null, "En turno"));
        guardar(new Asistencia(null, 3L, "Jorge Salas Medina", hoy, LocalTime.of(7, 5), LocalTime.of(15, 10), "Presente"));
        guardar(new Asistencia(null, 4L, "Milagros Paredes Luna", hoy, LocalTime.of(8, 15), null, "Tardanza"));
        guardar(new Asistencia(null, 5L, "Diego Huaman Torres", hoy, null, null, "Ausente"));
    }

    public List<Asistencia> listar() {
        return registros;
    }

    public Optional<Asistencia> buscarPorId(Long id) {
        return registros.stream().filter(a -> a.getId().equals(id)).findFirst();
    }

    public Asistencia guardar(Asistencia asistencia) {
        if (asistencia.getId() == null) {
            asistencia.setId(secuencia.incrementAndGet());
            registros.add(asistencia);
            return asistencia;
        }
        for (int i = 0; i < registros.size(); i++) {
            if (registros.get(i).getId().equals(asistencia.getId())) {
                registros.set(i, asistencia);
                return asistencia;
            }
        }
        registros.add(asistencia);
        return asistencia;
    }

    public void eliminar(Long id) {
        registros.removeIf(a -> a.getId().equals(id));
    }

    public long contarPorEstado(String estado) {
        return registros.stream().filter(a -> estado.equals(a.getEstado())).count();
    }
}
