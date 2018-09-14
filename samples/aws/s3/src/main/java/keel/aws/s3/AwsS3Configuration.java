package keel.aws.s3;

import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.transfer.TransferManager;
import com.amazonaws.services.s3.transfer.TransferManagerBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AwsS3Configuration {

    // transferManager-start
    @Bean(destroyMethod = "shutdownNow")
    public TransferManager transferManager(AmazonS3Client client) {
        return TransferManagerBuilder.standard()
                             .withS3Client(client)
                             .build();
    }
    // transferManager-end
}
