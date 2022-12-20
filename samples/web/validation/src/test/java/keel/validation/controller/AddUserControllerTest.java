package keel.validation.controller;

import static org.hamcrest.Matchers.notNullValue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.github.database.rider.core.api.configuration.DBUnit;
import com.github.database.rider.core.api.dataset.DataSet;
import com.github.database.rider.junit5.api.DBRider;
import keel.validation.ValidationApp;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

@DBRider
@DBUnit(schema = "PUBLIC")
@SpringBootTest(classes = ValidationApp.class)
@AutoConfigureMockMvc
public class AddUserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void 初期表示() throws Exception {
        mockMvc.perform(get("/"))
                .andExpect(status().isOk())
                .andExpect(view().name("index"));
    }

    @Test
    public void エラーがない場合は正常に処理が終了します() throws Exception {
        mockMvc.perform(post("/")
                    .param("name", "name")
                    .param("age", "30")
                    .param("mailAddress", "mail@example.com")
                    .param("role", "admin"))
               .andExpect(status().is3xxRedirection())
               .andExpect(flash().attribute("successMessage", "ユーザの登録を完了しました。"))
               .andExpect(view().name("redirect:/"));
    }

    @Test
    public void 年齢が数値以外の場合はバリデーションエラーとなります() throws Exception {
        mockMvc.perform(post("/")
                .param("name", "name")
                .param("age", "aa")
                .param("mailAddress", "invalid")
                .param("role", "admin"))
               .andExpect(status().isBadRequest())
               .andExpect(model().attributeHasFieldErrorCode(
                       "form", "age", "typeMismatch"));
    }

    @Test
    public void メールアドレスの形式が不正な場合バリデーションエラーとなります() throws Exception {
        mockMvc.perform(post("/")
                .param("name", "name")
                .param("mailAddress", "invalid")
                .param("role", "admin"))
               .andExpect(status().isBadRequest())
               .andExpect(model().attributeHasFieldErrorCode(
                       "form", "mailAddress", "typeMismatch"));
    }

    @Test
    @DataSet("users.yml")
    public void メールアドレスが重複している場合はバリデーションエラーとなります() throws Exception {
        mockMvc.perform(post("/")
                    .param("name", "name")
                    .param("age", "30")
                    .param("mailAddress", "hoge@example.com")
                    .param("role", "admin"))
               .andExpect(status().isBadRequest())
               .andExpect(model().attributeHasFieldErrors("form", "mailAddress"));
    }

    @Test
    public void ロールがadmin以外の場合はバリデーションエラーとなります() throws Exception {
        mockMvc.perform(post("/")
                    .param("name", "name")
                    .param("age", "30")
                    .param("mailAddress", "mail@example.com")
                    .param("role", "user"))
               .andExpect(status().isBadRequest())
               .andExpect(model().attributeHasFieldErrors("form", "role"));
    }
}
