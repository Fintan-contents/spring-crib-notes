package keel.sqlmanagement;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = SqlManagementApplication.class)
public class SqlManagementApplicationTest {

    @Test
    public void アプリケーション起動確認用のテスト() {
        System.out.println("success");
    }
}
