package com.octanogt.gasolinera.service;

import com.octanogt.gasolinera.model.DetalleVenta;
import com.octanogt.gasolinera.model.Venta;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;

@Service
public class VentaService {

    private final List<Venta> ventas = new ArrayList<>();
    private final AtomicLong secuenciaId = new AtomicLong(0);
    private final AtomicLong numeroVenta = new AtomicLong(1053);
    private final ProductoService productoService;

    public VentaService(ProductoService productoService) {
        this.productoService = productoService;
    }

    @PostConstruct
    public void cargarDatosDeEjemplo() {
        registrarEjemplo("Cliente generico", "Lucia Fernandez Rios", "Efectivo",
                new DetalleVenta(1L, "Gasohol 90", 10, 16.90), "Pagada", LocalDateTime.now().minusHours(2));
        registrarEjemplo("Transportes Andina S.A.C.", "Renzo Quispe Alarcon", "Tarjeta",
                new DetalleVenta(4L, "Diesel B5", 5.5, 17.40), "Pagada", LocalDateTime.now().minusHours(4));
        registrarEjemplo("Maria Elena Torres", "Lucia Fernandez Rios", "Billetera digital",
                new DetalleVenta(2L, "Gasohol 95", 12.5, 18.50), "Pagada", LocalDateTime.now().minusDays(1));
        registrarEjemplo("Cliente generico", "Renzo Quispe Alarcon", "Efectivo",
                new DetalleVenta(5L, "GLP", 7, 8.20), "Anulada", LocalDateTime.now().minusDays(1).minusHours(6));
    }

    private void registrarEjemplo(String cliente, String trabajador, String metodoPago,
                                   DetalleVenta detalle, String estado, LocalDateTime fecha) {
        Venta venta = new Venta();
        venta.setId(secuenciaId.incrementAndGet());
        venta.setNumero("#" + numeroVenta.incrementAndGet());
        venta.setFecha(fecha);
        venta.setClienteNombre(cliente);
        venta.setTrabajadorNombre(trabajador);
        venta.setMetodoPago(metodoPago);
        venta.getDetalles().add(detalle);
        venta.setTotal(detalle.getSubtotal());
        venta.setEstado(estado);
        ventas.add(venta);
    }

    public List<Venta> listar() {
        return ventas.stream()
                .sorted(Comparator.comparing(Venta::getFecha).reversed())
                .toList();
    }

    public Optional<Venta> buscarPorId(Long id) {
        return ventas.stream().filter(v -> v.getId().equals(id)).findFirst();
    }

    public Venta registrar(Venta venta) {
        venta.setId(secuenciaId.incrementAndGet());
        venta.setNumero("#" + numeroVenta.incrementAndGet());
        venta.setFecha(LocalDateTime.now());
        venta.setEstado("Pagada");

        double total = 0;
        for (DetalleVenta detalle : venta.getDetalles()) {
            total += detalle.getSubtotal();
            productoService.ajustarStock(detalle.getProductoId(), -detalle.getCantidad());
        }
        venta.setTotal(total);
        ventas.add(venta);
        return venta;
    }

    public void anular(Long id) {
        buscarPorId(id).ifPresent(v -> {
            if ("Pagada".equals(v.getEstado())) {
                for (DetalleVenta d : v.getDetalles()) {
                    productoService.ajustarStock(d.getProductoId(), d.getCantidad());
                }
                v.setEstado("Anulada");
            }
        });
    }
}
