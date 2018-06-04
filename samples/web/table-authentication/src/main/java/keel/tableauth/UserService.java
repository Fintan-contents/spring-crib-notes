package keel.tableauth;

import java.util.Collections;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

// example-start
// UserDetailsServiceを継承して、ユーザ名に紐づく情報を取得するloadUserByUsernameメソッドを実装します。
@Service
public class UserService implements UserDetailsService {

    private final UserDao userDao;

    public UserService(UserDao userDao) {
        this.userDao = userDao;
    }

    @Override
    public UserDetails loadUserByUsername(final String username) throws UsernameNotFoundException {
        // Daoを使用してユーザ情報を取得します。
        // ユーザ情報が存在しない場合には、UsernameNotFoundExceptionを送出し
        // Spring Security側での認証エラーの処理が行われるようにします。
        return userDao.loadUserByUserName(username)
                      .map(e -> new User(e.username, e.password, Collections.emptyList()))
                      .orElseThrow(() -> new UsernameNotFoundException("user not found. username:" + username));
    }
}
// example-end
