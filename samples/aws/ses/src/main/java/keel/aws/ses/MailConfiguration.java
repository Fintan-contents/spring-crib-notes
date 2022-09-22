package keel.aws.ses;

import com.amazonaws.auth.AWSCredentialsProvider;
import com.amazonaws.services.simpleemail.AmazonSimpleEmailServiceClient;
import io.awspring.cloud.core.config.AmazonWebserviceClientFactoryBean;
import io.awspring.cloud.core.region.StaticRegionProvider;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MailConfiguration {

    @Bean
    public AmazonWebserviceClientFactoryBean<AmazonSimpleEmailServiceClient> amazonSimpleEmailService(
            @Value("${aws.ses.region}") final String region,
            final AWSCredentialsProvider credentialsProvider) {
        return new AmazonWebserviceClientFactoryBean<>(AmazonSimpleEmailServiceClient.class,
                credentialsProvider, new StaticRegionProvider(region));
    }
}
