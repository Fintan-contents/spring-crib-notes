package keel.nablarch;

import nablarch.core.date.BusinessDateProvider;
import nablarch.core.date.SystemTimeUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Service
public class DateService {

    // bizdate-bind-start
    @Autowired
    private BusinessDateProvider businessDateProvider;
    // bizdate-bind-end

    public String getSystemDate() {
        // sysdate-start
        String systemDate = SystemTimeUtil.getDateString();
        // sysdate-end
        return systemDate;
    }

    public String getBusinessDate() {
        // bizdate-00-start
        String businessDate = businessDateProvider.getDate();
        // bizdate-00-end
        return businessDate;
    }

    public String getBusinessDateWithSegment(String segment) {
        // bizdate-01-start
        String businessDate = businessDateProvider.getDate(segment);
        // bizdate-01-end
        return businessDate;
    }

    public void updateBusinessDate(String segment, LocalDate date) {
        // update-start
        // 更新時の日付はyyyyMMdd形式の文字列で指定する（）
        String dateString = date.format(DateTimeFormatter.ofPattern("uuuuMMdd"));
        businessDateProvider.setDate(segment, dateString);
        // update-end
    }
}
