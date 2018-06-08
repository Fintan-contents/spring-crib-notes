package keel.validation.service;

import keel.validation.dao.UserDao;
import keel.validation.entity.User;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserDao userDao;

    public UserService(final UserDao userDao) {
        this.userDao = userDao;
    }

    // example-start
    public void insert(User user) {
        // ロールの存在チェック
        // 本来は外部キーの制約違反でハンドリングすべきですが、
        // ここでは実装例を示すためこのような実装にしています。
        if (!userDao.existsRole(user.role)) {
            throw new RoleNotFoundException();
        }
        userDao.insert(user);
    }
    // example-end
    
    public static class RoleNotFoundException extends RuntimeException {
        
    }
}
