package keel;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Doma2App.class)
@AutoConfigureMockMvc
public class Doma2AppTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Before
    public void setUp() throws Exception {
        jdbcTemplate.execute("truncate table users");
    }

    @Test
    public void Doma2を使ってデータベースに登録できること() throws Exception {
        mockMvc.perform(post("/users"))
               .andExpect(status().isOk());

        final Long usersCount = jdbcTemplate.queryForObject("select count(*) from users", Long.class);
        assertThat(usersCount)
                .isEqualTo(1);
    }
}