package keel.nablarch;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(classes = DateManagementApp.class, properties = { "business-date.fixed.01=20001231" })
class DateServiceFixedTest {

    @Autowired
    private DateService service;

    @Test
    void 固定した業務日付を取得できる() {
        String businessDate = service.getBusinessDateWithSegment("01");

        assertEquals("20001231", businessDate);
    }
}
