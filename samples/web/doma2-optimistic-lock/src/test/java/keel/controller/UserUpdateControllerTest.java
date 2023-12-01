package keel.controller;

import com.github.database.rider.core.api.configuration.DBUnit;
import com.github.database.rider.core.api.dataset.DataSet;
import com.github.database.rider.junit5.api.DBRider;
import keel.Doma2OptimisticLockApplication;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import jakarta.servlet.http.HttpSession;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@DBRider
@DBUnit(schema = "PUBLIC")
@SpringBootTest(classes = Doma2OptimisticLockApplication.class)
@AutoConfigureMockMvc
public class UserUpdateControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    @DataSet("users.yml")
    public void 初期表示がステータスコード200で終了すること() throws Exception {
        HttpSession session = mockMvc
                .perform(get("/user/edit"))
                .andExpect(status().isOk())
                .andExpect(model().hasNoErrors())
                .andExpect(view().name("user/edit"))
                .andReturn()
                .getRequest()
                .getSession();

        Assertions
                .assertThat(session.getAttribute("form"))
                .extracting("userId", "userName", "versionNo")
                .containsExactly(9999L, "jiro", 10L);
    }

    @Test
    @DataSet("users.yml")
    public void 更新処理が正常に完了しステータスコードが200で終了すること() throws Exception {
        HttpSession session = mockMvc
                .perform(get("/user/edit"))
                .andExpect(status().isOk())
                .andReturn()
                .getRequest()
                .getSession();

        mockMvc
                .perform(post("/user/update")
                        .param("userName", "キール")
                        .sessionAttr("form", session.getAttribute("form"))
                )
                .andExpect(status().is3xxRedirection())
                .andExpect(model().hasNoErrors())
                .andExpect(redirectedUrl("/user/edit?success"));
    }

    @Test
    public void 更新時の入力値チェックでエラーがあれば更新画面に戻る() throws Exception {
        mockMvc
                .perform(post("/user/update")
                        .param("id", "1")
                        .param("userName", "")
                )
                .andExpect(status().isOk())
                .andExpect(view().name("user/edit"));
    }
}
