package keel;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;

@SpringBootTest(classes = AccessLogApp.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class AccessLogAppTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    public void 起動確認() {
        final String body = restTemplate.getForObject("/api", String.class);
        Assertions
                .assertThat(body)
                .isEqualTo("hello world!!!");
    }
}
