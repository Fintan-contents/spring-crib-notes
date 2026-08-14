package keel.nablarch;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.resttestclient.TestRestTemplate;
import org.springframework.boot.resttestclient.autoconfigure.AutoConfigureTestRestTemplate;

import static org.junit.jupiter.api.Assertions.assertTrue;

// Spring Boot 4系ではTestRestTemplateが自動では登録されなくなったため、
// @AutoConfigureTestRestTemplateを付与する。
@AutoConfigureTestRestTemplate
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class NablarchValidationAppTest {

    @Autowired
    protected TestRestTemplate restTemplate;

    @Test
    public void 起動できる() throws Exception {
        String body = restTemplate.getForObject("/", String.class);

        assertTrue(body.contains("Nablarchバリデーション"));
    }
}
