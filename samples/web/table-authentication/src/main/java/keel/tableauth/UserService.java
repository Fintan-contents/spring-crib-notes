package keel.tableauth;

import static java.util.stream.Collectors.toList;

import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

// example-start
// UserDetailsServiceを実装して、ユーザ名に紐づく情報を取得するloadUserByUsernameメソッドを実装します。
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
                      // ユーザ情報には、ログインユーザに割り当てられた権限(ロール)も設定します。
                      .map(e -> new User(e.getUsername(), e.getPassword(), loadAuthorities(username)))
                      .orElseThrow(() -> new UsernameNotFoundException("user not found. username:" + username));
    }

    /**
     * ユーザ名に紐づく権限リストを取得します。
     *
     * @param username ユーザ名
     * @return ユーザ名に紐づく権限リスト（存在しない場合は空のリスト）
     */
    private List<GrantedAuthority> loadAuthorities(final String username) {
        return userDao.loadUserRoles(username)
                      .stream()
                      .map(SimpleGrantedAuthority::new)
                      .collect(toList());
    }
}
// example-end
