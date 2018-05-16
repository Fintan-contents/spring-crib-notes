package jp.co.tis.keel.controller;

import jp.co.tis.keel.Doma2OptimisticLockApplication;
import jp.co.tis.keel.controller.form.UserUpdateForm;
import jp.co.tis.keel.domain.service.UserService;
import jp.co.tis.keel.entity.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.seasar.doma.jdbc.OptimisticLockException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Doma2OptimisticLockApplication.class)
@AutoConfigureMockMvc
public class UserUpdateControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService service;

    @Test
    public void 初期表示がステータスコード200で終了すること() throws Exception {

        when(service.find()).thenReturn(new User(
                1L,
                "太郎",
                1L
        ));

        final UserUpdateForm expectedForm = new UserUpdateForm();
        expectedForm.setUserId(1L);
        expectedForm.setUserName("太郎");
        expectedForm.setVersionNo(1L);

        mockMvc
                .perform(get("/user/edit"))
                .andExpect(status().isOk())
                .andExpect(model().hasNoErrors())
                .andExpect(model().attribute("form", expectedForm))
                .andExpect(view().name("user/edit"));
    }

    @Test
    public void 更新処理が正常に完了しステータスコードが200で終了すること() throws Exception {

        when(service.update(any())).thenReturn(1);

        mockMvc
                .perform(post("/user/update")
                        .param("userName", "キール")
                )
                .andExpect(status().is3xxRedirection())
                .andExpect(model().hasNoErrors())
                .andExpect(redirectedUrl("/user/edit?success"));
    }

    @Test
    public void 更新処理で楽観ロック例外が発生してステータスコードが409で終了すること() throws Exception {
        doThrow(OptimisticLockException.class)
                .when(service)
                .update(any());

        mockMvc
                .perform(post("/user/update")
                        .param("userName", "キール")
                )
                .andExpect(status().isConflict());
    }
}
