package keel.apierrorhandling.service;

import com.github.springtestdbunit.DbUnitTestExecutionListener;
import com.github.springtestdbunit.annotation.DatabaseSetup;
import com.github.springtestdbunit.annotation.DbUnitConfiguration;
import keel.apierrorhandling.ApiErrorHandlingApp;
import keel.apierrorhandling.dbunit.CsvDataSetLoader;
import keel.apierrorhandling.entity.User;
import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.OptimisticLockingFailureException;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.transaction.TransactionalTestExecutionListener;

@RunWith(SpringRunner.class)
@TestExecutionListeners({
        DependencyInjectionTestExecutionListener.class,
        TransactionalTestExecutionListener.class,
        DbUnitTestExecutionListener.class})
@DbUnitConfiguration(dataSetLoader = CsvDataSetLoader.class)
@SpringBootTest(classes = {ApiErrorHandlingApp.class})
@DatabaseSetup("classpath:data/")
public class UserServiceTest {

    @Autowired
    UserService userService;

    @Test
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
