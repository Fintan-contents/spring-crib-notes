package keel.nablarch;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;

import static org.junit.jupiter.api.Assertions.assertTrue;

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
