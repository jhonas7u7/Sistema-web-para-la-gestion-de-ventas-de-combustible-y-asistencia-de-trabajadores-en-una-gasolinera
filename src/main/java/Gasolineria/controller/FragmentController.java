package Gasolineria.controller;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
@Controller
public class FragmentController {
    @GetMapping("/inicio")
    public String inicio() {
        return "inicio";
    }
}
