package keel.validation.dao;

import java.util.Optional;

import keel.validation.entity.Users;
import org.seasar.doma.Dao;
import org.seasar.doma.Insert;
import org.seasar.doma.Select;
import org.seasar.doma.boot.ConfigAutowireable;
import org.seasar.doma.jdbc.Result;

@Dao
@ConfigAutowireable
public interface UserDao {
    
    @Select
    Optional<String> findByRole(String role);
    
    default boolean existsRole(final String role) {
        return findByRole(role).isPresent();
    }

    @Insert
    Result<Users> insert(Users entity);
}
