package keel.apierrorhandling.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.database.rider.core.api.configuration.DBUnit;
import com.github.database.rider.core.api.dataset.DataSet;
import com.github.database.rider.junit5.api.DBRider;
import keel.apierrorhandling.ApiErrorHandlingApp;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Map;

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@DBRider
@DBUnit(schema = "PUBLIC")
@SpringBootTest(classes = ApiErrorHandlingApp.class)
@AutoConfigureMockMvc
public class UsersControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @Test
    @DataSet("users.yml")
    public void ユーザー一覧取得APIで全てのユーザーを取得できる() throws Exception {
        mockMvc
                .perform(get("/users"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$.[0].id").value(1))
                .andExpect(jsonPath("$.[0].name").value("taro"))
                .andExpect(jsonPath("$.[1].id").value(2))
                .andExpect(jsonPath("$.[1].name").value("hanako"));
    }

    @Test
    @DataSet("users.yml")
    public void ユーザー取得APIでユーザーを取得できる() throws Exception {
        mockMvc
                .perform(get("/users/2"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(2))
                .andExpect(jsonPath("$.name").value("hanako"));
    }

    @Test
    @DataSet("no-users.yml")
    public void ユーザー登録APIでユーザーを追加できる() throws Exception {
        mockMvc
                .perform(post("/users")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(objectMapper.writeValueAsString(Map.of(
                                "name", "piyo",
                                "age", 1,
                                "role", "admin"
                        )))
                )
                .andExpect(status().is2xxSuccessful())
                .andExpect(jsonPath("$.name").value("piyo"))
                .andReturn();

        // DBRiderのデータ登録ではid列のauto_incrementが進まないため、簡易的に件数のみ検証しておく
        mockMvc
                .perform(get("/users"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)));
    }

    @Test
    @DataSet("users.yml")
    public void ユーザー登録APIでユーザーを更新できる() throws Exception {
        mockMvc
                .perform(post("/users/1")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(objectMapper.writeValueAsString(Map.of(
                                "name", "hogehoge",
                                "age", 1,
                                "role", "user",
                                "versionNo", 10
                        )))
                )
                .andExpect(status().is2xxSuccessful())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("hogehoge"));

        mockMvc
                .perform(get("/users/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("hogehoge"));
    }
}
