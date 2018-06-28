package keel.validation.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import keel.validation.service.UserService;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(SpringRunner.class)
@WebMvcTest(AddUserController.class)
public class AddUserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    @Test
    public void エラーがない場合は正常に処理が終了します() throws Exception {
        // @formatter:off
        mockMvc.perform(post("/")
                    .param("name", "name")
                    .param("age", "30")
                    .param("mailAddress", "mail@example.com")
                    .param("role", "admin"))
               .andExpect(status().is3xxRedirection())
               .andExpect(flash().attribute("successMessage", "ユーザの登録を完了しました。"))
               .andExpect(view().name("redirect:/"));
        // @formatter:on
    }

    @Test
    public void 年齢が数値以外の場合はバリデーションエラーとなります() throws Exception {
        // @formatter:off
        mockMvc.perform(post("/")
                .param("name", "name")
                .param("age", "aa")
                .param("mailAddress", "invalid")
                .param("role", "admin"))
               .andExpect(status().isBadRequest())
               .andExpect(model().attributeHasFieldErrorCode(
                       "form", "age", "typeMismatch"));
        // @formatter:on
    }

    @Test
    public void メールアドレスの形式が不正な場合バリデーションエラーとなります() throws Exception {
        // @formatter:off
        mockMvc.perform(post("/")
                .param("name", "name")
                .param("mailAddress", "invalid")
                .param("role", "admin"))
               .andExpect(status().isBadRequest())
               .andExpect(model().attributeHasFieldErrorCode(
                       "form", "mailAddress", "typeMismatch"));
        // @formatter:on
    }

    @Test
    public void メールアドレスが重複している場合はバリデーションエラーとなります() throws Exception {
        Mockito.doThrow(DuplicateKeyException.class)
               .when(userService)
               .insert(any());

        // @formatter:off
        mockMvc.perform(post("/")
                    .param("name", "name")
                    .param("age", "30")
                    .param("mailAddress", "mail@example.com")
                    .param("role", "admin"))
               .andExpect(status().isBadRequest())
               .andExpect(model().attributeHasFieldErrors("form", "mailAddress"));
        // @formatter:on
    }

    @Test
    public void ロールがadmin以外の場合はバリデーションエラーとなります() throws Exception {
        Mockito.doThrow(UserService.RoleNotFoundException.class)
               .when(userService)
               .insert(any());

        // @formatter:off
        mockMvc.perform(post("/")
                    .param("name", "name")
                    .param("age", "30")
                    .param("mailAddress", "mail@example.com")
                    .param("role", "user"))
               .andExpect(status().isBadRequest())
               .andExpect(model().attributeHasFieldErrors("form", "role"));
        // @formatter:on
    }
}