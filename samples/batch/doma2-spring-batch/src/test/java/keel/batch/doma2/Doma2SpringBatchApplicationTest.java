package keel.batch.doma2;

import org.junit.jupiter.api.Test;
import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.test.JobLauncherTestUtils;
import org.springframework.batch.test.context.SpringBatchTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.TestPropertySource;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBatchTest
@SpringBootTest(classes = Doma2SpringBatchApplication.class)
@TestPropertySource("classpath:application-test.properties")
public class Doma2SpringBatchApplicationTest {

    @Autowired
    private JobLauncherTestUtils jobLauncherTestUtils;

    @Autowired
    private JdbcTemplate template;

    @Test
    public void ジョブが正常終了すること() throws Exception {
        JobExecution jobExecution = jobLauncherTestUtils.launchJob();
        assertThat(jobExecution.getStatus())
                .isEqualTo(BatchStatus.COMPLETED);

        Integer count = template.queryForObject("select count(*) from bonus;", Integer.class);
        assertThat(count)
                .isEqualTo(11);
    }
}
