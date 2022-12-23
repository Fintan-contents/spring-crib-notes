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
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@DBRider
@DBUnit(schema = "PUBLIC")
@SpringBootTest(classes = ApiErrorHandlingApp.class)
@AutoConfigureMockMvc
public class UsersController2Test {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @Test
    @DataSet("no-users.yml")
    public void ユーザー登録APIでユーザーを追加できる() throws Exception {
        mockMvc
                .perform(post("/users2")
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
}
