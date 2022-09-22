package keel;

import org.junit.jupiter.api.Test;
import org.seasar.doma.jdbc.NoResultException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@WebMvcTest(Users2Controller.class)
public class Users2ControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserDao mockDao;

    @Test
    public void 機能固有のNoResultExceptionのハンドリング確認テスト() throws Exception {
        doThrow(NoResultException.class)
                .when(mockDao)
                .getUserName(any());

        mockMvc
                .perform(get("/users2/1"))
                .andExpect(status().isNotFound())
                .andExpect(view().name("error/custom_404"));
    }

}
