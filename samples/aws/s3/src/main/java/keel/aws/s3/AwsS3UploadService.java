package keel.aws.s3;

import java.io.IOException;
import java.io.OutputStream;
import java.io.UncheckedIOException;
import java.nio.file.Files;
import java.nio.file.Path;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ResourceLoader;
import org.springframework.core.io.WritableResource;
import org.springframework.stereotype.Service;

// upload-start
@Service
public class AwsS3UploadService {

    private final Logger logger = LoggerFactory.getLogger(AwsS3UploadService.class);

    private final ResourceLoader resourceLoader;

    private final AwsS3Properties properties;

    public AwsS3UploadService(ResourceLoader resourceLoader, AwsS3Properties properties) {
        this.resourceLoader = resourceLoader;
        this.properties = properties;
    }

    public void uploadFile(Path path) {
        WritableResource writableResource = (WritableResource) resourceLoader.getResource(createObjectLocation(path));
        try (OutputStream outputStream = writableResource.getOutputStream()) {
            Files.copy(path, outputStream);

            logger.info("S3にファイルをアップロードしました。");
        } catch (IOException e) {
            throw new UncheckedIOException("S3へのファイルアップロードに失敗しました。", e);
        }
    }

    private String createObjectLocation(Path path) {
        return "s3://" + properties.getBucketName() + "/upload/" + path.getFileName();
    }
}
// upload-end
