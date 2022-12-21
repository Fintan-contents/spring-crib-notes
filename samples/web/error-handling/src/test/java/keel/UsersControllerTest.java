package keel;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(classes = ErrorHandlingApp.class)
@AutoConfigureMockMvc
public class UsersControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void 存在するユーザーを取得できる() throws Exception {
        mockMvc
                .perform(get("/users/1"))
                .andExpect(status().isOk());
    }

    @Test
    public void アプリケーション全体のNoResultExceptionのハンドリング確認テスト() throws Exception {
        mockMvc
                .perform(get("/users/999"))
                .andExpect(status().isNotFound());
    }
}
