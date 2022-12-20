package keel.tableauth;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestBuilders;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest(classes = TableAuthApp.class)
@AutoConfigureMockMvc
public class LoginTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void 存在しているユーザの情報でログインできること() throws Exception {
        mockMvc.perform(SecurityMockMvcRequestBuilders.formLogin()
                                                      .user("admin")
                                                      .password("password_"))
               .andDo(print())
               .andExpect(status().is3xxRedirection())
               .andExpect(redirectedUrl("/top"));
    }


    @Test
    public void ユーザが存在していない場合ログインエラーとなること() throws Exception {
        mockMvc.perform(SecurityMockMvcRequestBuilders.formLogin()
                                                      .user("dummy")
                                                      .password("dummy"))
               .andDo(print())
               .andExpect(status().is3xxRedirection())
               .andExpect(redirectedUrl("/login?error"));
    }
}
