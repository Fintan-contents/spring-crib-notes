package keel;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@SpringBootTest(classes = ErrorHandlingApp.class)
@AutoConfigureMockMvc
public class Users2ControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void 存在するユーザーを取得できる() throws Exception {
        mockMvc
                .perform(get("/users2/1"))
                .andExpect(status().isOk());
    }

    @Test
    public void 機能固有のNoResultExceptionのハンドリング確認テスト() throws Exception {
        mockMvc
                .perform(get("/users2/999"))
                .andExpect(status().isNotFound())
                .andExpect(view().name("error/custom_404"));
    }

}
