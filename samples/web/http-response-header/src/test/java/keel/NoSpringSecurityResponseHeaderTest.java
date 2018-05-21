package keel;

import org.assertj.core.api.SoftAssertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@TestPropertySource("classpath:test.properties")
@SpringBootTest(classes = HttpResponseHeaderApplication.class)
@AutoConfigureMockMvc(secure = false)
public class NoSpringSecurityResponseHeaderTest {

    @Autowired
    private MockMvc mvc;

    @WithMockUser
    @Test
    public void SpringSecurityを使用しない場合にデフォルトで設定されるヘッダの値を検証します() throws Exception {

        MockHttpServletResponse response = mvc
                .perform(
                        get("/")
                                .secure(true) // モックの動作をhttpsリクエストに設定します
                )
                .andExpect(status().isOk())
                .andReturn()
                .getResponse();

        System.out.println("◆設定されているヘッダー名出力 start◆");
        response
                .getHeaderNames()
                .forEach(System.out::println);
        System.out.println("◆設定されているヘッダー名出力 end◆");

        SoftAssertions
                .assertSoftly(softly -> {
                    softly
                            .assertThat(response.getHeader("Cache-Control"))
                            .isNull();
                    softly
                            .assertThat(response.getHeader("Pragma"))
                            .isNull();
                    softly
                            .assertThat(response.getHeader("Expires"))
                            .isNull();
                    softly
                            .assertThat(response.getHeader("X-Content-Type-Options"))
                            .isNull();
                    softly
                            .assertThat(response.getHeader("Strict-Transport-Security"))
                            .isNull();
                    softly
                            .assertThat(response.getHeader("X-Frame-Options"))
                            .isNull();
                    softly
                            .assertThat(response.getHeader("X-XSS-Protection"))
                            .isNull();
                });
    }
}
