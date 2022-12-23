package keel.nablarch;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.DefaultApplicationArguments;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(classes = DateManagementApp.class)
class DateManagementAppTest {

    @Autowired
    protected DateManagementApplicationRunner runner;

    @Test
    public void 起動できる() throws Exception {
        Assertions.assertDoesNotThrow(() -> runner.run(new DefaultApplicationArguments()));
    }
}
