package keel.domain.service;

import keel.domain.repository.UserDao;
import keel.domain.service.dto.UserDto;
import keel.entity.User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserService {

    private final UserDao userDao;

    public UserService(UserDao userDao) {
        this.userDao = userDao;
    }

    @Transactional(readOnly = true)
    public User find() {
        return userDao.find();
    }

    @Transactional
    public int update(UserDto dto) {
        return userDao
                .update(new User(
                        dto.getUserId(),
                        dto.getUserName(),
                        dto.getVersionNo()
                ))
                .getCount();
    }
}
