package com.octanogt.gasolinera.controller;

import com.octanogt.gasolinera.dto.LoginForm;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class AuthController {

    @GetMapping("/login")
    public String mostrarLogin(@RequestParam(value = "error", required = false) String error, Model model) {
        model.addAttribute("loginForm", new LoginForm());
        model.addAttribute("error", error != null);
        return "login";
    }

    @PostMapping("/login")
    public String procesarLogin(@ModelAttribute LoginForm loginForm) {
        boolean credencialesValidas = "admin".equalsIgnoreCase(loginForm.getUsuario())
                && "admin123".equals(loginForm.getContrasena());
        if (credencialesValidas) {
            return "redirect:/";
        }
        return "redirect:/login?error";
    }

    @GetMapping("/logout")
    public String cerrarSesion() {
        return "redirect:/login";
    }
}
