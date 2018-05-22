package keel;

import keel.entity.Users;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
public class Doma2App {
    
    private final UsersDao dao;

    @Autowired
    public Doma2App(final UsersDao dao) {
        this.dao = dao;
    }

    public static void main(String[] args) {
        SpringApplication.run(Doma2App.class, args);
    }
    
    @PostMapping(value = "/users", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public Users insert() {
        final Users entity = new Users();
        entity.setName("name_" + System.currentTimeMillis());
        dao.insert(entity);
        return entity;
    }
}
