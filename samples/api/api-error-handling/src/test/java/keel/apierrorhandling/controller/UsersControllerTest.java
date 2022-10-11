package keel.apierrorhandling.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import keel.apierrorhandling.ApiErrorHandlingApp;
import keel.apierrorhandling.service.UserService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.dao.OptimisticLockingFailureException;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest(classes = ApiErrorHandlingApp.class)
@AutoConfigureMockMvc
public class UsersControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService service;

    @Autowired
    ObjectMapper objectMapper;

    @Test
    public void アプリケーション全体のMethodArgumentNotValidExceptionのハンドリングのテスト() throws Exception {

        Map<String, Object> map = new HashMap<>();
        map.put("name", "");
        map.put("age", 1);
        map.put("role", "admin");
        String requestJson = objectMapper.writeValueAsString(map);

        mockMvc
                .perform(post("/users")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(requestJson)
                )
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$..field").value("name"))
                .andExpect(jsonPath("$..message").value("must not be empty"));
    }

    @Test
    public void アプリケーション全体のBindExceptionのハンドリングのテスト() throws Exception {

        mockMvc
                .perform(get("/users/find?name=taro&age=151&role=admin"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$..field").value("age"))
                .andExpect(jsonPath("$..message").value("must be less than or equal to 150"));
    }

    @Test
    public void アプリケーション全体の楽観ロックエラーのハンドリングのテスト() throws Exception {

        doThrow(OptimisticLockingFailureException.class)
                .when(service)
                .update(ArgumentMatchers.any());

        Map<String, Object> map = new HashMap<>();
        map.put("name", "taro");
        map.put("age", 1);
        map.put("role", "admin");
        map.put("versionNo", "1");
        String requestJson = objectMapper.writeValueAsString(map);


        mockMvc
                .perform(post("/users/1")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(requestJson)
                )
                .andExpect(status().isConflict())
                .andExpect(content().string(""));
    }

    @Test
    public void アプリケーション全体のUserNotFoundExceptionのハンドリングのテスト() throws Exception {

        when(service.getUser(any())).thenReturn(Optional.empty());

        final MockHttpServletResponse response = mockMvc
                .perform(get("/users/1"))
                .andExpect(status().isNotFound())
                .andReturn()
                .getResponse();

        Assertions
                .assertThat(response.getErrorMessage())
                .isEqualTo("ユーザーが見つかりませんでした。");
    }

    @Test
    public void アプリケーション全体のCustomValidationExceptionのハンドリングのテスト() throws Exception {

        doThrow(UserService.RoleNotFoundException.class)
                .when(service)
                .insert(ArgumentMatchers.any());

        Map<String, Object> map = new HashMap<>();
        map.put("name", "taro");
        map.put("age", 1);
        map.put("role", "admin");
        String requestJson = objectMapper.writeValueAsString(map);

        mockMvc
                .perform(post("/users")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(requestJson)
                )
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$..field").value("role"))
                .andExpect(jsonPath("$..message").value("ロール[admin]が見つかりませんでした。"));
    }

    @Test
    public void アプリケーション全体のHttpMessageNotReadableExceptionのハンドリングのテスト() throws Exception {

        mockMvc
                .perform(post("/users")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content("{name")
                )
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$").value("入力形式に誤りがあります。"));
    }
}
