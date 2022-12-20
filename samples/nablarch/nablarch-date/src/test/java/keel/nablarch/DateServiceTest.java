package keel.nablarch;

import com.github.database.rider.core.api.configuration.DBUnit;
import com.github.database.rider.core.api.dataset.DataSet;
import com.github.database.rider.junit5.api.DBRider;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@DBRider
@DBUnit(schema = "PUBLIC")
@SpringBootTest(classes = DateManagementApp.class)
class DateServiceTest {

    @Autowired
    private DateService service;

    @Test
    void システム日付を取得できる() {
        String systemDate = service.getSystemDate();

        assertNotNull(systemDate);
    }

    @Test
    @DataSet("business-dates.yml")
    void デフォルト業務日付を取得できる() {
        String businessDate = service.getBusinessDate();

        assertEquals("20220101", businessDate);
    }

    @Test
    @DataSet("business-dates.yml")
    void 区分を指定して業務日付を取得できる() {
        String businessDate = service.getBusinessDateWithSegment("01");

        assertEquals("20220102", businessDate);
    }

    @Test
    @DataSet("business-dates.yml")
    void 区分を指定して業務日付を更新できる() {
        service.updateBusinessDate("01", LocalDate.of(2100, 12, 31));

        String businessDate = service.getBusinessDateWithSegment("01");
        assertEquals("21001231", businessDate);
    }
}
