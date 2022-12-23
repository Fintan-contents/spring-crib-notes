package keel.nablarch.controller;

import keel.nablarch.CodeManagementApp;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@SpringBootTest(classes = CodeManagementApp.class)
@AutoConfigureMockMvc
class CodeManagementControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void 初期表示() throws Exception {
        mockMvc.perform(get("/"))
                .andExpect(status().isOk())
                .andExpect(view().name("index"));
    }

    @Test
    public void 入力値チェックでエラーが無い場合は完了画面を表示() throws Exception {
        mockMvc.perform(post("/")
                        .param("codePattern01", "MALE"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/"));
    }

    @Test
    public void 入力値チェックでエラーの場合は前画面に戻る() throws Exception {
        mockMvc.perform(post("/")
                        .param("codePattern01", "ERROR"))
                .andExpect(status().isBadRequest())
                .andExpect(view().name("index"));
    }
}
