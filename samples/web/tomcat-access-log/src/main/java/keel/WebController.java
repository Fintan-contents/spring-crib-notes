package keel;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class WebController {
    @GetMapping("/")
    public String get() {
        return "index";
    }

    @PostMapping("/")
    public String post() {
        return "index";
    }
}
