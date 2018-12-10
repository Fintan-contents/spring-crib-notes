package keel.aws.s3;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class AwsS3Application {

    public static void main(String[] args) {
        try (final ConfigurableApplicationContext ignored = SpringApplication.run(AwsS3Application.class, args)) {
            // nop
        }
    }
}
