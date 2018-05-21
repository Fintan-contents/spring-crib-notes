package keel;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class UsersController {

    private final UserDao userDao;

    public UsersController(final UserDao userDao) {
        this.userDao = userDao;
    }

    @GetMapping("/users/{id:[0-9]+}")
    @ResponseBody
    public String getUser(@PathVariable Long id) {
        final String name = userDao.getUserName(id);
        return "name->" + name;
    }
}
