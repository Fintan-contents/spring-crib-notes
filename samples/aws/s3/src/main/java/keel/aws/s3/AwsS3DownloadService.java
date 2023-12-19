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

// download-start
@Service
public class AwsS3DownloadService {

    private final Logger logger = LoggerFactory.getLogger(AwsS3DownloadService.class);

    private final S3Template s3Template;

    private final AwsS3Properties properties;

    public AwsS3DownloadService(S3Template s3Template, AwsS3Properties properties) {
        this.s3Template = s3Template;
        this.properties = properties;
    }

    public void downloadFile(Path path) {
        Path downloadPath = Path.of("download-" + path.getFileName());
        logger.info("アップロードされた{}をダウンロードし、{}に保存します。", path.getFileName(), downloadPath.getFileName());

        if (Files.exists(downloadPath)) {
            logger.info("既に{}が存在するためファイルの保存をスキップします。", downloadPath.getFileName());
            return;
        }

        try (InputStream inputStream = s3Template.download(
                properties.getBucketName(), Objects.toString(path.getFileName())).getInputStream()) {
            Files.copy(inputStream, downloadPath);
            logger.info("ファイルの保存に成功しました。");
        } catch (IOException e) {
            throw new UncheckedIOException("S3からのファイルダウンロードに失敗しました。", e);
        }
    }

    private String createObjectLocation(Path path) {
        return "s3://" + properties.getBucketName() + "/upload/" + path.getFileName();
    }
}
// download-end
