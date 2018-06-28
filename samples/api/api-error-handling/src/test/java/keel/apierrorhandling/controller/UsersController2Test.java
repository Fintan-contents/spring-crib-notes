package keel.apierrorhandling.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import keel.apierrorhandling.ApiErrorHandlingApp;
import keel.apierrorhandling.service.UserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.HashMap;
import java.util.Map;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = ApiErrorHandlingApp.class)
@AutoConfigureMockMvc
public class UsersController2Test {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService service;

    @Autowired
    ObjectMapper objectMapper;

    @Test
    public void 個別機能でMethodArgumentNotValidExceptionをハンドリングするテスト() throws Exception {

        Map<String, Object> map = new HashMap<>();
        map.put("name", "");
        map.put("age", 1);
        map.put("role", "admin");
        String requestJson = objectMapper.writeValueAsString(map);

        mockMvc
                .perform(post("/users2")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(requestJson)
                )
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$").value("入力項目に誤りがあります。"));
    }
}

