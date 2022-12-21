package keel.aws.ses;

import cloud.localstack.ServiceName;
import cloud.localstack.awssdkv1.TestUtils;
import cloud.localstack.docker.LocalstackDockerExtension;
import cloud.localstack.docker.annotation.LocalstackDockerProperties;
import com.amazonaws.services.simpleemail.AmazonSimpleEmailService;
import com.amazonaws.services.simpleemail.model.VerifyEmailAddressRequest;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.DefaultApplicationArguments;
import org.springframework.boot.test.context.ConfigDataApplicationContextInitializer;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

@SpringJUnitConfig(classes = AwsSesApplication.class, initializers = ConfigDataApplicationContextInitializer.class)
@ActiveProfiles("local")
@ExtendWith(LocalstackDockerExtension.class)
@LocalstackDockerProperties(services = { ServiceName.SES })
class ExampleApplicationRunnerTest {

    @Autowired
    private ExampleApplicationRunner runner;

    @BeforeAll
    static void beforeAll() {
        AmazonSimpleEmailService ses = TestUtils.getClientSES();
        VerifyEmailAddressRequest verifyRequest = new VerifyEmailAddressRequest();
        verifyRequest.setEmailAddress("foo@test.com");
        ses.verifyEmailAddress(verifyRequest);
    }

    @Test
    public void メールが送信される() throws Exception {
        Assertions.assertDoesNotThrow(() -> runner.run(new DefaultApplicationArguments()));

        Assertions.assertEquals(2, getSesMailCount());
    }

    private int getSesMailCount() throws Exception{
        HttpRequest request = HttpRequest.newBuilder().uri(URI.create("http://localhost:4566/_localstack/ses/")).GET().build();
        HttpClient http = HttpClient.newBuilder().build();
        HttpResponse<String> response = http.send(request, HttpResponse.BodyHandlers.ofString());
        String body = response.body();

        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode jsonNode = objectMapper.readTree(body);

        return jsonNode.get("messages").size();
    }
}
