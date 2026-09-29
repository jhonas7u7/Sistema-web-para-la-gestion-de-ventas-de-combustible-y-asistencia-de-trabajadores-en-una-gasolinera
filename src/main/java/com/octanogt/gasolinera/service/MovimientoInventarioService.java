package com.octanogt.gasolinera.service;

import com.octanogt.gasolinera.model.MovimientoInventario;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

@Service
public class MovimientoInventarioService {

    private final List<MovimientoInventario> movimientos = new ArrayList<>();
    private final AtomicLong secuencia = new AtomicLong(0);
    private final ProductoService productoService;

    public MovimientoInventarioService(ProductoService productoService) {
        this.productoService = productoService;
    }

    @PostConstruct
    public void cargarDatosDeEjemplo() {
        registrar(new MovimientoInventario(null, LocalDateTime.now().minusDays(1).withHour(9).withMinute(30),
                4L, "Diesel B5", "Entrada", 500, "Jorge Salas Medina", "Reabastecimiento programado"), false);
        registrar(new MovimientoInventario(null, LocalDateTime.now().minusDays(1).withHour(8).withMinute(10),
                1L, "Gasohol 90", "Salida", 320, "Sistema (ventas)", "Descuento automatico por ventas del turno"), false);
        registrar(new MovimientoInventario(null, LocalDateTime.now().minusDays(2).withHour(18).withMinute(45),
                7L, "Aditivo Premium 250ml", "Ajuste", -2, "Jorge Salas Medina", "Correccion tras inventario fisico"), false);
        registrar(new MovimientoInventario(null, LocalDateTime.now().minusDays(2).withHour(7).withMinute(0),
                5L, "GLP", "Entrada", 200, "Jorge Salas Medina", "Reabastecimiento programado"), false);
    }

    public List<MovimientoInventario> listar() {
        return movimientos.stream()
                .sorted(Comparator.comparing(MovimientoInventario::getFecha).reversed())
                .toList();
    }

    public MovimientoInventario registrar(MovimientoInventario movimiento, boolean aplicarEnInventario) {
        movimiento.setId(secuencia.incrementAndGet());
        if (movimiento.getFecha() == null) {
            movimiento.setFecha(LocalDateTime.now());
        }
        movimientos.add(movimiento);

        if (aplicarEnInventario) {
            double variacion = switch (movimiento.getTipo()) {
                case "Entrada" -> movimiento.getCantidad();
                case "Salida" -> -movimiento.getCantidad();
                default -> movimiento.getCantidad(); // Ajuste: puede ser positivo o negativo
            };
            productoService.ajustarStock(movimiento.getProductoId(), variacion);
        }
        return movimiento;
    }
}
