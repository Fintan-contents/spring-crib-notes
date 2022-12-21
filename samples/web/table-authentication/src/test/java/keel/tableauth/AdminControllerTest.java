package keel.tableauth;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

@SpringBootTest(classes = TableAuthApp.class)
public class AdminControllerTest {

    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext context;

    @BeforeEach
    public void setUp() throws Exception {
        mockMvc = MockMvcBuilders.webAppContextSetup(context)
                                 .apply(springSecurity())
                                 .build();
    }

    @Test
    @WithMockUser(roles = "admin")
    public void 管理者ユーザの場合管理者用ページにアクセス出来ること() throws Exception {
        mockMvc.perform(get("/admin"))
               .andDo(MockMvcResultHandlers.print())
               .andExpect(status().is2xxSuccessful())
               .andExpect(view().name("admin"));
    }

    @Test
    @WithMockUser(username = "user", roles = "user")
    public void 管理者ユーザではない場合は管理者用ページに権限エラーでアクセスできないこと() throws Exception {
        mockMvc.perform(get("/admin"))
               .andExpect(status().is(403));
    }

    @Test
    @WithMockUser(roles = "admin")
    public void 管理者ページからトップページに戻る() throws Exception {
        mockMvc.perform(post("/admin")
                        .param("back", "")
                        .with(csrf()))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().is2xxSuccessful())
                .andExpect(view().name("top"));
    }
}
