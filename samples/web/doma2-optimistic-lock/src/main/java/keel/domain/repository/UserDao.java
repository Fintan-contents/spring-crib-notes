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
    /**
     * 楽観ロックエラーが発生した場合は、{@link org.seasar.doma.jdbc.OptimisticLockException}が送出されます。
     * 呼出し元で例外を補足してハンドリングしてください。
     * <p/>
     * なお、サンプルアプリケーションでは{@link org.springframework.web.bind.annotation.ControllerAdvice}を使用して、
     * 全てのControllerを横断したエラーハンドリングを実施しています。
     * @see keel.controller.ErrorControllerAdvice
     */
    @Update
    Result<User> update(User user);
    // example-end
}
