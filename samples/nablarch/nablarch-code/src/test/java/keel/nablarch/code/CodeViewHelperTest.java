package keel.nablarch.code;

import keel.nablarch.CodeManagementApp;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(classes = CodeManagementApp.class)
class CodeViewHelperTest {

    @Autowired
    private CodeViewHelper helper;

    @Test
    void コード名称が取得できる() {
        String name = helper.getName("CODE01", "MALE");

        assertEquals("男性", name);
    }

    @Test
    void コード名称取得時に空のコード値に指定した場合はnullが返却される() {
        String name = helper.getName("CODE01", "");

        assertNull(name);
    }

    @Test
    void コード値を取得できる() {
        List<String> values = helper.getValues("CODE01", "pattern01");

        assertIterableEquals(List.of("MALE", "FEMALE"), values);
    }
}
