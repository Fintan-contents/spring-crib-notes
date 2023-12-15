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
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import static org.junit.jupiter.api.Assertions.assertEquals;

@MockServerTest("server.url=http://localhost:${mockServerPort}")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, properties = {
        "app.endpoint.get=${server.url}/user",
        "app.endpoint.post=${server.url}/users"
})
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

        ResponseEntity<User> response = restTemplate.getForEntity("http://localhost:" + port + "/user", User.class);

        assertEquals(200, response.getStatusCode().value());
        assertEquals("123", response.getBody().id());
        assertEquals("hoge", response.getBody().username());
    }

    @Test
    public void POSTリクエストが正常終了する() throws Exception {
        mockServerClient.when(HttpRequest
                        .request()
                        .withMethod("POST")
                        .withPath("/users"))
                .respond(HttpResponse
                        .response()
                        .withStatusCode(200)
                        .withHeader(
                                new Header("Content-Type", "application/json; charset=utf-8"))
                        .withBody("""
                  {
                    "id": "456",
                    "username": "fuga"
                  }
                  """));

        ResponseEntity<User> response = restTemplate.postForEntity("http://localhost:" + port + "/users",
                new User("456","fuga"), User.class);

        assertEquals(200, response.getStatusCode().value());
        assertEquals("456", response.getBody().id());
        assertEquals("fuga", response.getBody().username());
    }
}
