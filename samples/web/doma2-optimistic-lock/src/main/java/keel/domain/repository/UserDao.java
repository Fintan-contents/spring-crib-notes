package keel.domain.repository;

import keel.entity.User;
import org.seasar.doma.*;
import org.seasar.doma.boot.ConfigAutowireable;
import org.seasar.doma.jdbc.Result;

import java.util.List;
import java.util.Optional;

@ConfigAutowireable
@Dao
public interface UserDao {

    @Select
    Optional<User> find();

    // example-start
    @Update
    Result<User> update(User user);
    // example-end
}
