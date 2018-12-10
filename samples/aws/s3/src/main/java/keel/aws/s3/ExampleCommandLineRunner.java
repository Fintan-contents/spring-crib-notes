package keel.aws.s3;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class ExampleCommandLineRunner implements CommandLineRunner {

    private final AwsS3Service awsS3Service;

    public ExampleCommandLineRunner(final AwsS3Service awsS3Service) {
        this.awsS3Service = awsS3Service;
    }

    @Override
    public void run(final String... args) throws Exception {
        if (args.length != 1) {
            throw new IllegalArgumentException("アップロード対象のファイルパスを指定してください。");
        }

        final Path path = Paths.get(args[0]);
        if (!Files.exists(path)) {
            throw new IllegalArgumentException("アップロード対象のファイルが存在しません。");
        }
        awsS3Service.uploadFile(path);

        final String fileName = path.getFileName()
                                .toString();
        awsS3Service.downloadFile(fileName);
        awsS3Service.downloadFileByStream(fileName);
    }
}
