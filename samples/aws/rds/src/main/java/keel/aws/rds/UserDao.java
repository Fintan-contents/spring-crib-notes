package keel.aws.rds;

import java.util.List;

import org.seasar.doma.Dao;
import org.seasar.doma.Insert;
import org.seasar.doma.Select;
import org.seasar.doma.boot.ConfigAutowireable;
import org.seasar.doma.jdbc.Result;

@Dao
@ConfigAutowireable
public interface UserDao {

    @Select
    List<UsersEntity> findAll();

    @Insert
    Result<UsersEntity> insert(UsersEntity entity);
}
