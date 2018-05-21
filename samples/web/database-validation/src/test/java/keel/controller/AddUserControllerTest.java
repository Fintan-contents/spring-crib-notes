package keel.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import keel.service.UserService;
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
                    .param("mailAddress", "mail@example.com")
                    .param("role", "admin"))
               .andExpect(status().is3xxRedirection())
               .andExpect(view().name("redirect:/"));
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
                    .param("mailAddress", "mail@example.com")
                    .param("role", "user"))
               .andExpect(status().isBadRequest())
               .andExpect(model().attributeHasFieldErrors("form", "role"));
        // @formatter:on
    }
}