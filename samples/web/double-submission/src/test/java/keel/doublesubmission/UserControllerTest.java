package keel.doublesubmission;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import jp.fintan.keel.spring.web.token.transaction.TransactionToken;
import jp.fintan.keel.spring.web.token.transaction.TransactionTokenInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = DoubleSubmissionApplication.class)
@AutoConfigureMockMvc
public class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService service;

    @Test
    public void トークンが生成されているかを確認するテスト() throws Exception {
        mockMvc
                .perform(post("/user/confirm")
                        .param("name", "taro")
                        .param("age", "25")
                )
                .andExpect(status().isOk())
                .andExpect(model().hasNoErrors())
                .andExpect(view().name("user/confirm"))
                .andExpect(xpath("//*[@name=\"_TRANSACTION_TOKEN\"]/@value").exists());

    }

    @Test
    public void トークンをリクエスト送信しない場合のテスト() throws Exception {
        mockMvc
                .perform(post("/user/create")
                        .param("name", "taro")
                        .param("age", "25")
                )
                .andExpect(status().isBadRequest())
                .andExpect(view().name("error/token-error"));
    }

    @Test
    public void トークンをリクエスト送信する場合のテスト() throws Exception {

        final MockHttpServletRequest request = mockMvc
                .perform(post("/user/confirm")
                        .param("name", "taro")
                        .param("age", "25")
                )
                .andExpect(status().isOk())
                .andReturn()
                .getRequest();

        MockHttpSession session = (MockHttpSession) request.getSession();
        TransactionToken transactionToken = (TransactionToken) request.getAttribute(TransactionTokenInterceptor.NEXT_TOKEN_REQUEST_ATTRIBUTE_NAME);

        mockMvc
                .perform(
                        post("/user/create")
                                .param("name", "taro")
                                .param("age", "25")
                                .param("_TRANSACTION_TOKEN", transactionToken.getTokenString())
                                .session(session))
                .andExpect(redirectedUrl("/user/complete"));
    }
}
