package keel.nablarch;

import nablarch.core.date.BusinessDateProvider;
import nablarch.core.date.SystemTimeUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Service
public class DateService {

    @Autowired
    private BusinessDateProvider businessDateProvider;

    // sysdate-start
    /**
     * システム日付を取得します。
     *
     * @return システム日付(yyyyMMdd形式)
     */
    public String getSystemDate() {
        String systemDate = SystemTimeUtil.getDateString();
        return systemDate;
    }
    // sysdate-end

    // bizdate-00-start
    /**
     * デフォルト区分の業務日付を取得します。
     *
     * @return 業務日付(yyyyMMdd形式)
     */
    public String getBusinessDate() {
        String businessDate = businessDateProvider.getDate();
        return businessDate;
    }
    // bizdate-00-end


    // bizdate-01-start
    /**
     * 指定された区分の業務日付を取得します。
     *
     * @param segment 区分
     * @return 業務日付(yyyyMMdd形式)
     */
    public String getBusinessDateWithSegment(String segment) {
        String businessDate = businessDateProvider.getDate(segment);
        return businessDate;
    }
    // bizdate-01-end

    // bizdate-update-start
    /**
     * 指定された区分の業務日付を更新します。
     *
     * @param segment 区分
     * @param date 日付
     */
    public void updateBusinessDate(String segment, LocalDate date) {
        // 更新時の日付はyyyyMMdd形式の文字列で指定する
        String dateString = date.format(DateTimeFormatter.ofPattern("uuuuMMdd"));
        businessDateProvider.setDate(segment, dateString);
    }
    // bizdate-update-end
}
