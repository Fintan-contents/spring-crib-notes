package keel.aws.ses;

import org.junit.jupiter.api.Assertions;
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
import org.testcontainers.shaded.com.fasterxml.jackson.databind.JsonNode;
import org.testcontainers.shaded.com.fasterxml.jackson.databind.ObjectMapper;
import org.testcontainers.utility.DockerImageName;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

@SpringJUnitConfig(classes = AwsSesApplication.class, initializers = ConfigDataApplicationContextInitializer.class)
@Testcontainers
class ExampleApplicationRunnerTest {

    @Autowired
    private ExampleApplicationRunner runner;

    @Container
    static LocalStackContainer localStack = new LocalStackContainer(
            DockerImageName.parse("localstack/localstack:3.0.2"))
            .withServices(LocalStackContainer.Service.SES);

    @DynamicPropertySource
    static void overrideProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.cloud.aws.region.static",
                () -> localStack.getRegion());
        registry.add("spring.cloud.aws.credentials.access-key",
                () -> localStack.getAccessKey());
        registry.add("spring.cloud.aws.credentials.secret-key",
                () -> localStack.getSecretKey());
        registry.add("spring.cloud.aws.ses.endpoint",
                () -> localStack.getEndpointOverride(LocalStackContainer.Service.SES).toString());
    }

    @BeforeAll
    static void beforeAll() throws Exception {
        localStack.execInContainer("awslocal", "ses", "verify-email-identity", "--email", "foo@test.com");
    }

    @Test
    public void メールが送信される() throws Exception {
        Assertions.assertDoesNotThrow(() -> runner.run(new DefaultApplicationArguments()));

        Assertions.assertEquals(2, getSesMailCount());
    }

    private int getSesMailCount() throws Exception{
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(localStack.getEndpoint() + "/_aws/ses/"))
                .GET()
                .build();
        HttpClient http = HttpClient.newBuilder().build();
        HttpResponse<String> response = http.send(request, HttpResponse.BodyHandlers.ofString());
        String body = response.body();

        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode jsonNode = objectMapper.readTree(body);

        return jsonNode.get("messages").size();
    }
}
