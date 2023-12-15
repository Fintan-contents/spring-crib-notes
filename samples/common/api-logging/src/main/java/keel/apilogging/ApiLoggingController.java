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

    @Value("${app.endpoint.get}")
    private String endpointForGet;

    @Value("${app.endpoint.post}")
    private String endpointForPost;

    public ApiLoggingController(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    // logbook-start
    @GetMapping("/user")
    public User get() {
        // Logbookを設定したRestTemplateを使用する
        ResponseEntity<User> response = restTemplate.getForEntity(endpointForGet, User.class);

        return response.getBody();
    }
    // logbook-end

    @PostMapping("/users")
    public User post(@RequestBody User user) {
        // Logbookを設定したRestTemplateを使用する
        ResponseEntity<User> response = restTemplate.postForEntity(endpointForPost, user, User.class);

        return response.getBody();
    }
}
