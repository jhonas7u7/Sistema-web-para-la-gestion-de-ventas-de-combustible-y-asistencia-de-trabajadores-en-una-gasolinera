package com.gasolinera.app.controller;

import com.gasolinera.app.model.Usuario;
import com.gasolinera.app.service.UsuarioService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/usuarios")
public class UsuarioController {

    private final UsuarioService usuarioService;

    public UsuarioController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    @PostMapping("/guardar")
    public String guardarUsuario(@ModelAttribute("usuarioForm") Usuario usuario) {
        usuarioService.guardarUsuario(usuario);
        return "redirect:/usuarios";
    }

    @PostMapping("/eliminar")
    public String eliminarUsuario(@RequestParam("id") Long id) {
        usuarioService.eliminarUsuario(id);
        return "redirect:/usuarios";
    }

    @GetMapping
    public String listarUsuarios(
            @RequestParam(name = "buscar", required = false) String buscar,
            @RequestParam(name = "estado", required = false) String estado,
            Model model) {

        List<Usuario> lista = usuarioService.listarFiltrados(buscar, estado);

        model.addAttribute("listaUsuarios", lista);
        model.addAttribute("criterioBusqueda", buscar != null ? buscar : "");
        model.addAttribute("estadoSeleccionado", estado != null ? estado : "Todos los estados");
        model.addAttribute("usuarioForm", new Usuario());

        return "usuarios";
    }
}