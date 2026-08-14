package keel;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.resttestclient.TestRestTemplate;
import org.springframework.boot.resttestclient.autoconfigure.AutoConfigureTestRestTemplate;

import static org.assertj.core.api.Assertions.assertThat;

// Spring Boot 4系ではTestRestTemplateが自動では登録されなくなったため、
// @AutoConfigureTestRestTemplateを付与する。
@AutoConfigureTestRestTemplate
@SpringBootTest(classes = AccessLogApp.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class WebControllerTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    public void webにGETリクエスト() {
        String body = restTemplate.getForObject("/", String.class);

        assertThat(body).contains("アクセスログ出力のサンプル");
    }

    @Test
    public void webにPOSTリクエスト() {
        String body = restTemplate.postForObject("/", null, String.class);

        assertThat(body).contains("アクセスログ出力のサンプル");
    }
}
