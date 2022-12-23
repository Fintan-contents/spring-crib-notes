package keel.nablarch;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
public class DateManagementApplicationRunner implements ApplicationRunner {

    private final DateService service;

    public DateManagementApplicationRunner(DateService service) {
        this.service = service;
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        System.out.println("--------------------------------------------------");
        System.out.println("システム日付: " + service.getSystemDate());
        System.out.println("業務日付(00): " + service.getBusinessDate());
        System.out.println("業務日付(01): " + service.getBusinessDateWithSegment("01"));
        System.out.println("--------------------------------------------------");

        service.updateBusinessDate("01", LocalDate.now());

        System.out.println("--------------------------------------------------");
        System.out.println("業務日付(01)をシステム日付と同日に更新しました。");
        System.out.println("--------------------------------------------------");

        System.out.println("--------------------------------------------------");
        System.out.println("システム日付: " + service.getSystemDate());
        System.out.println("業務日付(00): " + service.getBusinessDate());
        System.out.println("業務日付(01): " + service.getBusinessDateWithSegment("01"));
        System.out.println("--------------------------------------------------");
    }
}
