package com.octanogt.gasolinera.service;

import com.octanogt.gasolinera.model.Usuario;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;

@Service
public class UsuarioService {

    private final List<Usuario> usuarios = new ArrayList<>();
    private final AtomicLong secuencia = new AtomicLong(0);

    @PostConstruct
    public void cargarDatosDeEjemplo() {
        guardar(new Usuario(null, "Marcos Ramirez", "mramirez", "mramirez@octanogt.com", "Administrador", "Activo"));
        guardar(new Usuario(null, "Lucia Fernandez", "lfernandez", "lfernandez@octanogt.com", "Encargado de ventas", "Activo"));
        guardar(new Usuario(null, "Renzo Quispe", "rquispe", "rquispe@octanogt.com", "Trabajador", "Activo"));
        guardar(new Usuario(null, "Jorge Salas", "jsalas", "jsalas@octanogt.com", "Encargado de inventario", "Activo"));
        guardar(new Usuario(null, "Milagros Paredes", "mparedes", "mparedes@octanogt.com", "Trabajador", "Inactivo"));
    }

    public List<Usuario> listar() {
        return usuarios;
    }

    public Optional<Usuario> buscarPorId(Long id) {
        return usuarios.stream().filter(u -> u.getId().equals(id)).findFirst();
    }

    public Usuario guardar(Usuario usuario) {
        if (usuario.getId() == null) {
            usuario.setId(secuencia.incrementAndGet());
            usuarios.add(usuario);
            return usuario;
        }
        for (int i = 0; i < usuarios.size(); i++) {
            if (usuarios.get(i).getId().equals(usuario.getId())) {
                usuarios.set(i, usuario);
                return usuario;
            }
        }
        usuarios.add(usuario);
        return usuario;
    }

    public void eliminar(Long id) {
        usuarios.removeIf(u -> u.getId().equals(id));
    }
}
