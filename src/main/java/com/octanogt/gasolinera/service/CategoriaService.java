package com.octanogt.gasolinera.service;

import com.octanogt.gasolinera.model.Categoria;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;

@Service
public class CategoriaService {

    private final List<Categoria> categorias = new ArrayList<>();
    private final AtomicLong secuencia = new AtomicLong(0);

    @PostConstruct
    public void cargarDatosDeEjemplo() {
        guardar(new Categoria(null, "Combustibles", "Gasoholes, diesel y GLP", "Activo"));
        guardar(new Categoria(null, "Lubricantes", "Aceites y aditivos para motor", "Activo"));
        guardar(new Categoria(null, "Accesorios", "Articulos y repuestos menores", "Activo"));
        guardar(new Categoria(null, "Servicios", "Servicios adicionales de la estacion", "Inactivo"));
    }

    public List<Categoria> listar() {
        return categorias;
    }

    public List<Categoria> listarActivas() {
        return categorias.stream().filter(c -> "Activo".equals(c.getEstado())).toList();
    }

    public Optional<Categoria> buscarPorId(Long id) {
        return categorias.stream().filter(c -> c.getId().equals(id)).findFirst();
    }

    public Categoria guardar(Categoria categoria) {
        if (categoria.getId() == null) {
            categoria.setId(secuencia.incrementAndGet());
            categorias.add(categoria);
            return categoria;
        }
        for (int i = 0; i < categorias.size(); i++) {
            if (categorias.get(i).getId().equals(categoria.getId())) {
                categorias.set(i, categoria);
                return categoria;
            }
        }
        categorias.add(categoria);
        return categoria;
    }

    public void desactivar(Long id) {
        buscarPorId(id).ifPresent(c -> c.setEstado("Inactivo"));
    }
}
