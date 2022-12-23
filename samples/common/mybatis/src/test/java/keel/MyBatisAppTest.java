package keel;

import static org.hamcrest.Matchers.hasSize;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest(classes = MyBatisApp.class)
@AutoConfigureMockMvc
public class MyBatisAppTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @BeforeEach
    public void setUp() throws Exception {
        jdbcTemplate.execute("truncate table users");
    }

    @Test
    public void MyBatisを使ってデータベースに登録できること() throws Exception {
        mockMvc.perform(post("/users"))
               .andExpect(status().isOk());

        Long usersCount = jdbcTemplate.queryForObject("SELECT COUNT(*) FROM users", Long.class);
        assertEquals(1, usersCount);
    }

    @Test
    public void MyBatisを使ってデータベースから取得できること() throws Exception {
        jdbcTemplate.update("INSERT INTO users (id, name) VALUES (?, ?)",1, "name_1");

        mockMvc.perform(get("/users"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$.[0].id").value(1))
                .andExpect(jsonPath("$.[0].name").value("name_1"));
    }
}
