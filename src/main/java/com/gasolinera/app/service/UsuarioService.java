package com.gasolinera.app.service;

import com.gasolinera.app.model.Usuario;
import com.gasolinera.app.repository.UsuarioRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;

    public UsuarioService(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    public List<Usuario> obtenerTodos() {
        return usuarioRepository.listarTodos();
    }

    public void guardarUsuario(Usuario usuario) {
        if (usuario.getEstado() == null || usuario.getEstado().isBlank()) {
            usuario.setEstado("Activo");
        }
        usuarioRepository.guardar(usuario);
    }

    public void eliminarUsuario(Long id) {
        usuarioRepository.eliminarPorId(id);
    }

    public List<Usuario> listarFiltrados(String criterio, String estado) {
        return usuarioRepository.buscarYFiltrar(criterio, estado);
    }
}