package keel.sqlmanagement;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(classes = SqlManagementApplication.class)
public class SqlManagementApplicationTest {

    @Test
    public void アプリケーション起動確認用のテスト() {
        System.out.println("success");
    }
}
