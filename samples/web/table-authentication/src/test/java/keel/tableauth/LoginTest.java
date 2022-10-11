package keel.tableauth;

import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Collections;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestBuilders;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

@SpringBootTest(classes = TableAuthApp.class)
public class LoginTest {

    private MockMvc mockMvc;

    private PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @MockBean
    private UserService mockService;

    @Autowired
    private WebApplicationContext context;

    @BeforeEach
    public void setUp() throws Exception {
        mockMvc = MockMvcBuilders.webAppContextSetup(context)
                                 .apply(springSecurity())
                                 .build();
    }

    @Test
    public void 存在しているユーザの情報でログインできること() throws Exception {
        when(mockService.loadUserByUsername("admin"))
                .thenReturn(new User("admin", passwordEncoder.encode("password"), Collections.emptyList()));

        mockMvc.perform(SecurityMockMvcRequestBuilders.formLogin()
                                                      .user("admin")
                                                      .password("password"))
               .andDo(print())
               .andExpect(status().is3xxRedirection())
               .andExpect(redirectedUrl("/top"));
    }


    @Test
    public void ユーザが存在していない場合ログインエラーとなること() throws Exception {
        when(mockService.loadUserByUsername("admin"))
                .thenThrow(new UsernameNotFoundException("user not found."));

        mockMvc.perform(SecurityMockMvcRequestBuilders.formLogin()
                                                      .user("admin")
                                                      .password("password"))
               .andDo(print())
               .andExpect(status().is3xxRedirection())
               .andExpect(redirectedUrl("/login?error"));
    }
}
