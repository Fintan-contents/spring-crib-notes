package keel.listener;

import keel.entity.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.listener.JobExecutionListenerSupport;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class UserJobCompletionListener extends JobExecutionListenerSupport {

    private static final Logger log = LoggerFactory.getLogger(UserJobCompletionListener.class);

    private final JdbcTemplate jdbcTemplate;

    public UserJobCompletionListener(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void afterJob(JobExecution jobExecution) {
        if (jobExecution.getStatus() == BatchStatus.COMPLETED) {
            log.info("job finished");

            jdbcTemplate
                    .query("SELECT user_id, user_name FROM users",
                            (rs, row) -> new User(
                                    rs.getString(1),
                                    rs.getString(2)
                            )
                    )
                    .forEach(user ->
                            log.info(user.toString())
                    );
        }
    }
}
