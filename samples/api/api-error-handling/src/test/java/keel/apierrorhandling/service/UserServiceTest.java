package keel.apierrorhandling.service;

import com.github.database.rider.core.api.configuration.DBUnit;
import com.github.database.rider.core.api.dataset.DataSet;
import com.github.database.rider.junit5.api.DBRider;
import keel.apierrorhandling.ApiErrorHandlingApp;
import keel.apierrorhandling.entity.User;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.OptimisticLockingFailureException;

@DBRider
@DBUnit(schema = "PUBLIC")
@SpringBootTest(classes = {ApiErrorHandlingApp.class})
public class UserServiceTest {

    @Autowired
    UserService userService;

    @Test
    @DataSet("users.yml")
    public void 楽観ロックエラー時にOptimisticLockingFailureExceptionが発生するかを検証するテスト() {

        Assertions
                .assertThatThrownBy(() -> {
                    userService.update(new User(
                            1L,
                            "jiro",
                            "admin",
                            20,
                            9L
                    ));
                })
                .isInstanceOf(OptimisticLockingFailureException.class);

    }
}
