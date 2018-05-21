package keel;

import keel.entity.Users;
import org.seasar.doma.Dao;
import org.seasar.doma.Insert;
import org.seasar.doma.boot.ConfigAutowireable;

@Dao
@ConfigAutowireable
public interface UsersDao {

    @Insert
    int insert(Users entity);
}
