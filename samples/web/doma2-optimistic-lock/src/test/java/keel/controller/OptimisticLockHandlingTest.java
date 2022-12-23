package keel.controller;

import keel.Doma2OptimisticLockApplication;
import keel.domain.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.dao.OptimisticLockingFailureException;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(classes = Doma2OptimisticLockApplication.class)
@AutoConfigureMockMvc
public class OptimisticLockHandlingTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService service;

    @Test
    public void 更新処理で楽観ロック例外が発生してステータスコードが409で終了すること() throws Exception {
        doThrow(OptimisticLockingFailureException.class)
                .when(service)
                .update(any());

        mockMvc
                .perform(post("/user/update")
                        .param("userName", "キール")
                )
                .andExpect(status().isConflict());
    }
}
