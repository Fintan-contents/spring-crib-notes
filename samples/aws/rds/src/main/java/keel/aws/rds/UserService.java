package keel.aws.rds;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserService {

    private final UserDao userDao;

    public UserService(final UserDao userDao) {
        this.userDao = userDao;
    }
    
    // readonly-start
    @Transactional(readOnly = true)
    public List<UsersEntity> findAll() {
        return userDao.findAll();
    }
    // readonly-end
    
    @Transactional
    public UsersEntity insert(UsersEntity entity) {
        return userDao.insert(entity)
                      .getEntity();
    }
}
