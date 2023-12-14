package keel.apilogging;

import org.junit.jupiter.api.Test;
import org.mockserver.client.MockServerClient;
import org.mockserver.model.Header;
import org.mockserver.model.HttpRequest;
import org.mockserver.model.HttpResponse;
import org.mockserver.springtest.MockServerTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

@MockServerTest("server.url=http://localhost:${mockServerPort}")
@SpringBootTest(
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
        properties = { "app.endpoint=${server.url}/user" })
class ApiLoggingControllerTest {

    @LocalServerPort
    int port;

    MockServerClient mockServerClient;

    @Autowired
    RestTemplate restTemplate;

    @Test
    public void GETリクエストが正常終了する() throws Exception {
        mockServerClient.when(HttpRequest
                .request()
                .withMethod("GET")
                .withPath("/user"))
                .respond(HttpResponse
                        .response()
                        .withStatusCode(200)
                        .withHeader(
                                new Header("Content-Type", "application/json; charset=utf-8"))
                        .withBody("""
                  {
                    "id": "123",
                    "username": "hoge"
                  }
                  """));

        Map<String, String> response = restTemplate.getForObject("http://localhost:" + port + "/user", Map.class);

        assertEquals("123", response.get("id"));
        assertEquals("hoge", response.get("username"));
    }
}
