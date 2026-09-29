package com.octanogt.gasolinera.service;

import com.octanogt.gasolinera.model.Producto;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;

@Service
public class ProductoService {

    private final List<Producto> productos = new ArrayList<>();
    private final AtomicLong secuencia = new AtomicLong(0);

    @PostConstruct
    public void cargarDatosDeEjemplo() {
        guardar(new Producto(null, "P-001", "Gasohol 90", "Combustibles", 16.90, 4200, "gal", 1500, "Activo"));
        guardar(new Producto(null, "P-002", "Gasohol 95", "Combustibles", 18.50, 3800, "gal", 1500, "Activo"));
        guardar(new Producto(null, "P-003", "Gasohol 97", "Combustibles", 19.90, 1200, "gal", 1000, "Activo"));
        guardar(new Producto(null, "P-004", "Diesel B5", "Combustibles", 17.40, 5000, "gal", 2000, "Activo"));
        guardar(new Producto(null, "P-005", "GLP", "Combustibles", 8.20, 900, "gal", 1000, "Activo"));
        guardar(new Producto(null, "P-006", "Aceite de motor 4T 1L", "Lubricantes", 32.00, 45, "unid.", 20, "Activo"));
        guardar(new Producto(null, "P-007", "Aditivo Premium 250ml", "Lubricantes", 24.50, 6, "unid.", 15, "Activo"));
    }

    public List<Producto> listar() {
        return productos;
    }

    public List<Producto> listarActivos() {
        return productos.stream().filter(p -> "Activo".equals(p.getEstado())).toList();
    }

    public Optional<Producto> buscarPorId(Long id) {
        return productos.stream().filter(p -> p.getId().equals(id)).findFirst();
    }

    public Producto guardar(Producto producto) {
        if (producto.getId() == null) {
            producto.setId(secuencia.incrementAndGet());
            productos.add(producto);
            return producto;
        }
        for (int i = 0; i < productos.size(); i++) {
            if (productos.get(i).getId().equals(producto.getId())) {
                productos.set(i, producto);
                return producto;
            }
        }
        productos.add(producto);
        return producto;
    }

    public void eliminar(Long id) {
        productos.removeIf(p -> p.getId().equals(id));
    }


    public void ajustarStock(Long productoId, double cantidad) {
        buscarPorId(productoId).ifPresent(p -> p.setStock(p.getStock() + cantidad));
    }
}
