package keel.aws.s3;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.DefaultApplicationArguments;
import org.springframework.boot.test.context.ConfigDataApplicationContextInitializer;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import org.testcontainers.containers.localstack.LocalStackContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;

import java.nio.file.Files;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

@SpringJUnitConfig(classes = AwsS3Application.class, initializers = ConfigDataApplicationContextInitializer.class)
@Testcontainers
class ExampleApplicationRunnerTest {

    @Autowired
    private ExampleApplicationRunner runner;

    @Container
    static LocalStackContainer localStack = new LocalStackContainer(
            DockerImageName.parse("localstack/localstack:3.0.2"))
            .withServices(LocalStackContainer.Service.S3);

    @DynamicPropertySource
    static void overrideProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.cloud.aws.region.static",
                () -> localStack.getRegion());
        registry.add("spring.cloud.aws.credentials.access-key",
                () -> localStack.getAccessKey());
        registry.add("spring.cloud.aws.credentials.secret-key",
                () -> localStack.getSecretKey());
        registry.add("spring.cloud.aws.s3.endpoint",
                () -> localStack.getEndpointOverride(LocalStackContainer.Service.S3).toString());
    }

    @BeforeAll
    static void beforeAll() throws Exception {
        localStack.execInContainer("awslocal", "s3", "mb", "s3://keel-s3-bucket-test");
    }

    @Test
    public void ファイルのアップロードとダウンロードができる() throws Exception {
        Files.deleteIfExists(Paths.get("download-test.txt"));

        assertDoesNotThrow(() -> runner.run(new DefaultApplicationArguments(
                Paths.get("src/test/resources/test.txt").toAbsolutePath().toString())));
    }
}
