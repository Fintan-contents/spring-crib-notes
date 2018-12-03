package keel;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.seasar.doma.jdbc.NoResultException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(UsersController.class)
public class UsersControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserDao mockDao;

    @Test
    public void アプリケーション全体のNoResultExceptionのハンドリング確認テスト() throws Exception {
        doThrow(NoResultException.class)
                .when(mockDao)
                .getUserName(any());

        mockMvc
                .perform(get("/users/1"))
                .andExpect(status().isNotFound());
    }
}
