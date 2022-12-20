package keel.aws.s3;

import cloud.localstack.ServiceName;
import cloud.localstack.awssdkv1.TestUtils;
import cloud.localstack.docker.LocalstackDockerExtension;
import cloud.localstack.docker.annotation.LocalstackDockerProperties;
import com.amazonaws.services.s3.AmazonS3;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.DefaultApplicationArguments;
import org.springframework.boot.test.context.ConfigDataApplicationContextInitializer;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.*;

@SpringJUnitConfig(classes = AwsS3Application.class, initializers = ConfigDataApplicationContextInitializer.class)
@ActiveProfiles("local")
@ExtendWith(LocalstackDockerExtension.class)
@LocalstackDockerProperties(services = { ServiceName.S3 })
class ExampleApplicationRunnerTest {

    @Autowired
    private ExampleApplicationRunner runner;

    @BeforeAll
    static void beforeAll() {
        AmazonS3 s3 = TestUtils.getClientS3();
        s3.createBucket("keel-s3-bucket-test");
    }

    @Test
    public void ファイルのアップロードとダウンロードができる() throws Exception {
        Files.deleteIfExists(Paths.get("download-test.txt"));

        assertDoesNotThrow(() -> runner.run(new DefaultApplicationArguments(
                Paths.get("src/test/resources/test.txt").toAbsolutePath().toString())));
    }
}
