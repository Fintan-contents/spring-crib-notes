package keel.aws.s3;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "s3")
public class AwsS3Properties {
    
    private String bucketName;

    public String getBucketName() {
        return bucketName;
    }

    public void setBucketName(final String bucketName) {
        this.bucketName = bucketName;
    }
}
