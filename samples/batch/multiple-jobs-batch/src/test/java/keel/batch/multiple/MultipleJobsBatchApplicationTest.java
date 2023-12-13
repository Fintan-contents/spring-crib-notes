package keel.batch.multiple;

import org.junit.jupiter.api.Test;
import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.test.JobLauncherTestUtils;
import org.springframework.batch.test.context.SpringBatchTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.TestPropertySource;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBatchTest
@SpringBootTest(classes = MultipleJobsBatchApplication.class)
@TestPropertySource("classpath:application-test.properties")
public class MultipleJobsBatchApplicationTest {

    @Autowired
    private JobLauncherTestUtils jobLauncherTestUtils;

    @Autowired
    private JdbcTemplate template;

    @Autowired
    @Qualifier("job1")
    Job job1;

    @Test
    public void ジョブが正常終了すること() throws Exception {
        jobLauncherTestUtils.setJob(job1);
        JobExecution jobExecution = jobLauncherTestUtils.launchJob();

        assertThat(jobExecution.getStatus())
                .isEqualTo(BatchStatus.COMPLETED);
    }
}
