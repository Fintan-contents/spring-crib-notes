package keel;

import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(classes = SpringSecurityHttpResponseHeaderApplication.class)
@AutoConfigureMockMvc
public class SpringSecurityResponseHeaderTest {

    @Autowired
    private MockMvc mvc;

    @WithMockUser
    @Test
    public void SpringSecurityを使用した場合の動的コンテンツに設定されているヘッダの値を検証します() throws Exception {

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
                            .isEqualTo("no-cache, no-store, max-age=0, must-revalidate");
                    softly
                            .assertThat(response.getHeader("Pragma"))
                            .isEqualTo("no-cache");
                    softly
                            .assertThat(response.getHeader("Expires"))
                            .isEqualTo("0");
                    softly
                            .assertThat(response.getHeader("X-Content-Type-Options"))
                            .isEqualTo("nosniff");
                    // HTTPS通信の場合に付与されます
                    softly
                            .assertThat(response.getHeader("Strict-Transport-Security"))
                            .isEqualTo("max-age=31536000 ; includeSubDomains");
                    softly
                            .assertThat(response.getHeader("X-Frame-Options"))
                            .isEqualTo("DENY");
                    softly
                            .assertThat(response.getHeader("X-XSS-Protection"))
                            .isEqualTo("0");
                });
    }

    @WithMockUser
    @Test
    public void SpringSecurityを使用した場合の静的コンテンツに設定されているヘッダの値を検証します() throws Exception {

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
                            .isEqualTo("nosniff");
                    // HTTPS通信の場合に付与されます
                    softly
                            .assertThat(response.getHeader("Strict-Transport-Security"))
                            .isEqualTo("max-age=31536000 ; includeSubDomains");
                    softly
                            .assertThat(response.getHeader("X-Frame-Options"))
                            .isEqualTo("DENY");
                    softly
                            .assertThat(response.getHeader("X-XSS-Protection"))
                            .isEqualTo("0");
                });
    }
}
