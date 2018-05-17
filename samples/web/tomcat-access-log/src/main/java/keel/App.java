package keel;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
public class App {

    public static void main(String[] args) {
        SpringApplication.run(App.class, args);
    }

    @Controller
    public static class Web {
        @GetMapping("/")
        public String get() {
            return "index";
        }

        @PostMapping("/")
        public String post() {
            return "index";
        }
    }
    
    @RestController
    public static class Api {
        
        @GetMapping("/api")
        @ResponseBody
        public String get() {
            return "hello world!!!";
        }
    }
}
