package keel.validation.service;

import keel.validation.dao.RoleDao;
import keel.validation.dao.UserDao;
import keel.validation.entity.User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserService {

    private final UserDao userDao;

    private final RoleDao roleDao;

    public UserService(final UserDao userDao, final RoleDao roleDao) {
        this.userDao = userDao;
        this.roleDao = roleDao;
    }

    // example-start
    @Transactional
    public void insert(User user) {
        // ロールの存在チェック
        // 本来は外部キーの制約違反でハンドリングすべきですが、
        // ここでは実装例を示すためこのような実装にしています。
        if (!roleDao.existsRole(user.role)) {
            throw new RoleNotFoundException();
        }
        userDao.insert(user);
    }
    // example-end
    
    public static class RoleNotFoundException extends RuntimeException {
        
    }
}
