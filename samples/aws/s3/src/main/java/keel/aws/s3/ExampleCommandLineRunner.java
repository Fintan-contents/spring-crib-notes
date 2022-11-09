package keel.aws.s3;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Component
public class ExampleCommandLineRunner implements ApplicationRunner {

    private final AwsS3UploadService awsS3UploadService;

    private final AwsS3DownloadService awsS3DownloadService;

    public ExampleCommandLineRunner(AwsS3UploadService awsS3UploadService, AwsS3DownloadService awsS3DownloadService) {
        this.awsS3UploadService = awsS3UploadService;
        this.awsS3DownloadService = awsS3DownloadService;
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        List<String> optionArgs = args.getNonOptionArgs();
        if (optionArgs.size() != 1) {
            throw new IllegalArgumentException("アップロード対象のファイルパスを指定してください。");
        }

        final Path path = Path.of(optionArgs.get(0));
        if (!Files.exists(path)) {
            throw new IllegalArgumentException("アップロード対象のファイルが存在しません。");
        }
        awsS3UploadService.uploadFile(path);

        awsS3DownloadService.downloadFile(path);
    }
}
