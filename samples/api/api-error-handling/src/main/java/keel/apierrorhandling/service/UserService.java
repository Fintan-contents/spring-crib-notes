package keel.apierrorhandling.service;

import keel.apierrorhandling.dao.UserDao;
import keel.apierrorhandling.dao.criteria.UserCriteria;
import keel.apierrorhandling.entity.User;
import org.seasar.doma.jdbc.Result;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class UserService {

    private final UserDao userDao;

    public UserService(UserDao userDao) {
        this.userDao = userDao;
    }

    public List<User> findAllUsers() {
        return userDao.findAllUsers();
    }

    public List<User> findUsers(UserCriteria criteria) {
        return userDao.findUsers(criteria);
    }

    public Optional<User> getUser(Long id) {
        return userDao.getUser(id);
    }

    public Result<User> insert(User user) {
        // ロールの存在チェック
        if (!userDao.existsRole(user.role)) {
            throw new RoleNotFoundException();
        }
        return userDao.insert(user);
    }

    public Result<User> update(User user) {
        // ロールの存在チェック
        if (!userDao.existsRole(user.role)) {
            throw new RoleNotFoundException();
        }
        return userDao.update(user);
    }

    public static class RoleNotFoundException extends RuntimeException {
    }
}
