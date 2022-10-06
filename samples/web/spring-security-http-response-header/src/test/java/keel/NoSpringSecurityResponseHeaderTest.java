package keel;

import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@TestPropertySource("classpath:test.properties")
@SpringBootTest(classes = SpringSecurityHttpResponseHeaderApplication.class)
@AutoConfigureMockMvc
public class NoSpringSecurityResponseHeaderTest {

    @Autowired
    private MockMvc mvc;

    @Test
    public void SpringSecurityを使用しない場合の動的コンテンツに設定されているヘッダの値を検証します() throws Exception {

        MockHttpServletResponse response = mvc
                .perform(
                        get("/")
                                .secure(true) // モックの動作をhttps通信にします
                )
                .andExpect(status().isOk())
                .andReturn()
                .getResponse();

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

    @Test
    public void SpringSecurityを使用しない場合の静的コンテンツに設定されているヘッダの値を検証します() throws Exception {

        MockHttpServletResponse response = mvc
                .perform(
                        get("/css/base.css")
                                .secure(true) // モックの動作をhttps通信にします
                )
                .andExpect(status().isOk())
                .andReturn()
                .getResponse();

        SoftAssertions
                .assertSoftly(softly -> {
                    softly
                            .assertThat(response.getHeader("Cache-Control"))
                            .isEqualTo("max-age=86400");
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
