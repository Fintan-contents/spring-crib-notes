package keel.batch.doma2;

import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableBatchProcessing
public class Doma2SpringBatchApplication {
    public static void main(String[] args) {
        SpringApplication.run(Doma2SpringBatchApplication.class, args);
    }
}
