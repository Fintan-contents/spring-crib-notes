package jp.co.tis.keel.domain.service;

import jp.co.tis.keel.domain.repository.UserDao;
import jp.co.tis.keel.domain.service.dto.PasswordUpdateDto;
import jp.co.tis.keel.domain.service.exception.UserNotFoundException;
import jp.co.tis.keel.entity.User;
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
        return userDao
                .find()
                .orElseThrow(
                        () -> new UserNotFoundException
                                ("user not found."));
    }

    @Transactional
    public int update(PasswordUpdateDto dto) {
        return userDao
                .update(new User(
                        dto.getUserId(),
                        dto.getUserName(),
                        dto.getVersionNo()
                ))
                .getCount();
    }
}
