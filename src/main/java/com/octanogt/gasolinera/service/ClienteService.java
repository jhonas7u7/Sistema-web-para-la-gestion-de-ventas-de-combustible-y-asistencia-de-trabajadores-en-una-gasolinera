package com.octanogt.gasolinera.service;

import com.octanogt.gasolinera.model.Cliente;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;

@Service
public class ClienteService {

    private final List<Cliente> clientes = new ArrayList<>();
    private final AtomicLong secuencia = new AtomicLong(0);

    @PostConstruct
    public void cargarDatosDeEjemplo() {
        guardar(new Cliente(null, "Transportes Andina S.A.C.", "20456789123", "987 222 111", "contacto@transandina.com", "Activo"));
        guardar(new Cliente(null, "Maria Elena Torres", "45678912", "987 333 222", "metorres@gmail.com", "Activo"));
        guardar(new Cliente(null, "Comercial Vega E.I.R.L.", "20678912345", "987 444 333", "ventas@comercialvega.com", "Activo"));
        guardar(new Cliente(null, "Pedro Injante Salazar", "41234567", "987 555 444", "pedro.injante@gmail.com", "Inactivo"));
        guardar(new Cliente(null, "Cliente generico", "-", "-", "-", "Activo"));
    }

    public List<Cliente> listar() {
        return clientes;
    }

    public List<Cliente> listarActivos() {
        return clientes.stream().filter(c -> "Activo".equals(c.getEstado())).toList();
    }

    public Optional<Cliente> buscarPorId(Long id) {
        return clientes.stream().filter(c -> c.getId().equals(id)).findFirst();
    }

    public Cliente guardar(Cliente cliente) {
        if (cliente.getId() == null) {
            cliente.setId(secuencia.incrementAndGet());
            clientes.add(cliente);
            return cliente;
        }
        for (int i = 0; i < clientes.size(); i++) {
            if (clientes.get(i).getId().equals(cliente.getId())) {
                clientes.set(i, cliente);
                return cliente;
            }
        }
        clientes.add(cliente);
        return cliente;
    }

    public void eliminar(Long id) {
        clientes.removeIf(c -> c.getId().equals(id));
    }
}
