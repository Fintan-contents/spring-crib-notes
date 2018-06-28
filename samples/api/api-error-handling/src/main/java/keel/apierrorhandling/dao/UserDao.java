package keel.apierrorhandling.dao;

import keel.apierrorhandling.dao.criteria.UserCriteria;
import keel.apierrorhandling.entity.User;
import org.seasar.doma.Dao;
import org.seasar.doma.Insert;
import org.seasar.doma.Select;
import org.seasar.doma.Update;
import org.seasar.doma.boot.ConfigAutowireable;
import org.seasar.doma.jdbc.Result;

import java.util.List;
import java.util.Optional;

@Dao
@ConfigAutowireable
public interface UserDao {

    @Select
    List<User> findAllUsers();

    @Select
    List<User> findUsers(UserCriteria criteria);

    @Select
    Optional<User> getUser(Long id);

    @Insert
    Result<User> insert(User user);

    //optimistic-lock-example-start

    /**
     * 楽観ロックエラーが発生した場合は、{@link org.springframework.dao.OptimisticLockingFailureException}が送出されます。
     * 呼出し元で例外を補足してハンドリングしてください。
     * <p/>
     * なお、サンプルアプリケーションでは{@link org.springframework.web.bind.annotation.RestControllerAdvice}を使用して、
     * 全てのControllerを横断したエラーハンドリングを実施しています。
     *
     * @see keel.apierrorhandling.GlobalExceptionHandler
     */
    @Update
    Result<User> update(User user);
    //optimistic-lock-example-end

    @Select
    Optional<String> getRole(String role);

    default boolean existsRole(String role) {
        return this
                .getRole(role)
                .isPresent();
    }
}
