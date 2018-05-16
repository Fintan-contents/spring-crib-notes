package jp.co.tis.keel.domain.repository;

import jp.co.tis.keel.entity.User;
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

    @Update
    Result<User> update(User user);
}
