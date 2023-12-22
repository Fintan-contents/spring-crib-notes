package keel.aws.s3;

import java.io.IOException;
import java.io.InputStream;
import java.io.UncheckedIOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Objects;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import io.awspring.cloud.s3.S3Template;

// upload-start
@Service
public class AwsS3UploadService {

    private final Logger logger = LoggerFactory.getLogger(AwsS3UploadService.class);

    private final S3Template s3Template;

    private final AwsS3Properties properties;

    public AwsS3UploadService(S3Template s3Template, AwsS3Properties properties) {
        this.s3Template = s3Template;
        this.properties = properties;
    }

    public void uploadFile(Path path) {
        logger.info("{}をS3にアップロードします。", path.getFileName());

        try (InputStream inputStream = Files.newInputStream(path)) {
            s3Template.upload(properties.getBucketName(), Objects.toString(path.getFileName()), inputStream);
            logger.info("ファイルのアップロードに成功しました。");
        } catch (IOException e) {
            throw new UncheckedIOException("S3へのファイルアップロードに失敗しました。", e);
        }
    }
}
// upload-end
