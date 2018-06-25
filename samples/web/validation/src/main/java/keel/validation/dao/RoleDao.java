package keel.validation.dao;

import java.util.Optional;

import keel.validation.entity.Role;
import org.seasar.doma.Dao;
import org.seasar.doma.Select;
import org.seasar.doma.boot.ConfigAutowireable;

@Dao
@ConfigAutowireable
public interface RoleDao {

    @Select
    Optional<Role> findOne(String role);

    default boolean existsRole(final String role) {
        return findOne(role).isPresent();
    }
}
