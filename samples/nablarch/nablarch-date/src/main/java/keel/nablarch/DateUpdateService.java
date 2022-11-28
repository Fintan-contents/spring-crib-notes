package keel.nablarch;

import nablarch.core.date.BusinessDateProvider;
import nablarch.core.date.SystemTimeUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Service
public class DateUpdateService {

    // bizdate-update-start
    @Autowired
    private BusinessDateProvider businessDateProvider;

    public void updateBusinessDate(String segment, LocalDate date) {
        // 更新時の日付はyyyyMMdd形式の文字列で指定する（）
        String dateString = date.format(DateTimeFormatter.ofPattern("uuuuMMdd"));
        businessDateProvider.setDate(segment, dateString);
    }
    // bizdate-update-end
}
