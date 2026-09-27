package com.gasolinera.app.repository;

import com.gasolinera.app.model.Usuario;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;

@Repository
public class UsuarioRepository {

    private final List<Usuario> usuarios = new ArrayList<>();
    private final AtomicLong contadorId = new AtomicLong(1);

    public UsuarioRepository() {
        guardar(new Usuario(null, "Marcos Ramírez", "mramirez", "mramirez@octanogt.com", "Administrador", "Activo"));
        guardar(new Usuario(null, "Lucía Fernández", "lfernandez", "lfernandez@octanogt.com", "Encargado de ventas", "Activo"));
        guardar(new Usuario(null, "Renzo Quispe", "rquispe", "rquispe@octanogt.com", "Trabajador", "Activo"));
        guardar(new Usuario(null, "Jorge Salas", "jsalas", "jsalas@octanogt.com", "Encargado de inventario", "Activo"));
        guardar(new Usuario(null, "Milagros Paredes", "mparedes", "mparedes@octanogt.com", "Trabajador", "Inactivo"));
    }

    public List<Usuario> listarTodos() {
        return new ArrayList<>(usuarios);
    }

    public Optional<Usuario> buscarPorId(Long id) {
        return usuarios.stream().filter(u -> u.getId().equals(id)).findFirst();
    }

    public Usuario guardar(Usuario usuario) {
        if (usuario.getId() == null) {
            usuario.setId(contadorId.getAndIncrement());
            usuarios.add(usuario);
        } else {
            buscarPorId(usuario.getId()).ifPresent(u -> {
                u.setNombre(usuario.getNombre());
                u.setUsername(usuario.getUsername());
                u.setEmail(usuario.getEmail());
                u.setRol(usuario.getRol());
                u.setEstado(usuario.getEstado());
            });
        }
        return usuario;
    }

    public void eliminarPorId(Long id) {
        usuarios.removeIf(u -> u.getId().equals(id));
    }

    public List<Usuario> buscarYFiltrar(String criterio, String estado) {
        return usuarios.stream()
                .filter(u -> {
                    // Filtro por Estado
                    boolean coincideEstado = (estado == null || estado.isBlank() || estado.equalsIgnoreCase("Todos los estados"))
                            || (u.getEstado() != null && u.getEstado().equalsIgnoreCase(estado));

                    // Filtro por Nombre, Usuario o Correo
                    boolean coincideTexto = true;
                    if (criterio != null && !criterio.isBlank()) {
                        String query = criterio.trim().toLowerCase();
                        coincideTexto = (u.getNombre() != null && u.getNombre().toLowerCase().contains(query))
                                || (u.getUsername() != null && u.getUsername().toLowerCase().contains(query))
                                || (u.getEmail() != null && u.getEmail().toLowerCase().contains(query));
                    }

                    return coincideEstado && coincideTexto;
                })
                .toList();
    }
}