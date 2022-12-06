package keel.tableauth;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("top")
public class TopController {

    @GetMapping
    public String get() {
        return "top";
    }
}
