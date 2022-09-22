package keel.domain.service;

import com.github.database.rider.core.api.configuration.DBUnit;
import com.github.database.rider.core.api.dataset.DataSet;
import com.github.database.rider.junit5.api.DBRider;
import keel.Doma2OptimisticLockApplication;
import keel.domain.service.dto.UserDto;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.OptimisticLockingFailureException;

@DBRider
@DBUnit(schema = "PUBLIC")
@SpringBootTest(classes = {Doma2OptimisticLockApplication.class})
public class UserServiceTest {

    @Autowired
    UserService userService;

    @Test
    @DataSet("users.yml")
    public void 楽観ロックエラー時にOptimisticLockingFailureExceptionが発生するかを検証するテスト() {

        Assertions
                .assertThatThrownBy(() -> {
                    userService.update(new UserDto(
                            9999L,
                            "jiro",
                            9L
                    ));
                })
                .isInstanceOf(OptimisticLockingFailureException.class);

    }
}
