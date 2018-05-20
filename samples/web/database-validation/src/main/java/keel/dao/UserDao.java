package keel.dao;

import java.util.Optional;

import keel.entity.Users;
import org.seasar.doma.Dao;
import org.seasar.doma.Insert;
import org.seasar.doma.Select;
import org.seasar.doma.boot.ConfigAutowireable;
import org.seasar.doma.jdbc.Result;

@Dao
@ConfigAutowireable
public interface UserDao {
    
    @Select
    Optional<String> findOne(String role);
    
    default boolean existsRole(final String role) {
        return findOne(role).isPresent();
    }

    @Insert
    Result<Users> insert(Users entity);
}
