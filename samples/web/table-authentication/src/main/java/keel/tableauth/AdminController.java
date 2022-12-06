package keel.tableauth;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin")
public class AdminController {

    @GetMapping
    public String get() {
        return "/admin";
    }

    @PostMapping(params = "back")
    public String back() {
        return "/top";
    }
}
