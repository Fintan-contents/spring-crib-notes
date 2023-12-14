package keel.apilogging;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
public class ApiLoggingController {

    private final RestTemplate restTemplate;

    @Value("${app.endpoint}")
    private String endpoint;

    public ApiLoggingController(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @GetMapping("/user")
    public User get() {
        // Logbookを設定したRestTemplateを使用する
        ResponseEntity<User> response = restTemplate.getForEntity(endpoint, User.class);

        return response.getBody();
    }

    @PostMapping("/users")
    public User post(@RequestBody User user) {
        // Logbookを設定したRestTemplateを使用する
        ResponseEntity<User> response = restTemplate.postForEntity(endpoint, user, User.class);

        return response.getBody();
    }
}
