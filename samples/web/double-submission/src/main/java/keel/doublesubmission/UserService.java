package keel.doublesubmission;

import org.seasar.doma.jdbc.Result;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserService {

    private final UserDao userDao;

    public UserService(UserDao userDao) {
        this.userDao = userDao;
    }

    @Transactional
    public Result<User> insert(User user) {
        return userDao.insert(user);
    }
}
