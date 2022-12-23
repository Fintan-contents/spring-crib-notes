package keel;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserMapper mapper;

    public UserController(UserMapper mapper) {
        this.mapper = mapper;
    }

    @PostMapping
    public User insert() {
        User entity = new User("name_" + System.currentTimeMillis());
        mapper.insert(entity);
        return entity;
    }

    @GetMapping
    public List<User> list() {
        return mapper.list();
    }
}
