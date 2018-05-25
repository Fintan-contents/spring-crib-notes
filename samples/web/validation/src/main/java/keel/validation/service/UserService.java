package keel.validation.service;

import keel.validation.dao.UserDao;
import keel.validation.entity.Users;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserDao userDao;

    public UserService(final UserDao userDao) {
        this.userDao = userDao;
    }

    // example-start
    public void insert(Users users) {
        // ロールの存在チェック
        // 本来は外部キーの制約違反でハンドリングすべきですが、
        // ここでは実装例を示すためこのような実装にしています。
        if (!userDao.existsRole(users.role)) {
            throw new RoleNotFoundException();
        }
        userDao.insert(users);
    }
    // example-end
    
    public static class RoleNotFoundException extends RuntimeException {
        
    }
}
