package keel.tableauth;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@SpringBootApplication
public class TableAuthApp {

    public static void main(String[] args) {
        SpringApplication.run(TableAuthApp.class, args);
    }

    @Configuration
    public static class WebConfig implements WebMvcConfigurer {

        @Override
        public void addViewControllers(final ViewControllerRegistry registry) {
            registry.addViewController("/login")
                    .setViewName("login");
        }
    }

    @Controller
    public static class TopController {

        @GetMapping("/top")
        public String top() {
            return "top";
        }
    }

    @Controller
    public static class AdminController {

        @GetMapping("/admin")
        public String admin() {
            return "/admin";
        }
    }
}
