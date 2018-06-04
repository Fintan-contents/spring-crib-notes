package keel.doublesubmission;

import org.seasar.doma.Dao;
import org.seasar.doma.Insert;
import org.seasar.doma.boot.ConfigAutowireable;
import org.seasar.doma.jdbc.Result;

@Dao
@ConfigAutowireable
public interface UserDao {

    @Insert
    Result<User> insert(User user);
}
