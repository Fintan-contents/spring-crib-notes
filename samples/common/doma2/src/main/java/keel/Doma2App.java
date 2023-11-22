package keel;

import org.seasar.doma.jdbc.Result;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
public class Doma2App {
    
    private final UsersDao dao;

    public Doma2App(final UsersDao dao) {
        this.dao = dao;
    }

    public static void main(String[] args) {
        SpringApplication.run(Doma2App.class, args);
    }
    
    @PostMapping(value = "/users", produces = MediaType.APPLICATION_JSON_VALUE)
    public User insert() {
        final User entity = new User("name_" + System.currentTimeMillis());
        Result<User> result = dao.insert(entity);
        return result.getEntity();
    }
}
