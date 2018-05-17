package keel;

import ch.qos.logback.classic.Logger;
import ch.qos.logback.core.Appender;
import keel.processor.UserItemProcessor;
import org.assertj.core.api.SoftAssertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.slf4j.LoggerFactory;
import org.slf4j.event.LoggingEvent;
import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.test.JobLauncherTestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {BatchBeanValidationMessagesApplication.class, UserJobTest.BatchTestConfig.class})
@TestPropertySource("classpath:test.properties")
public class UserJobTest {

    @Autowired
    JobLauncherTestUtils jobLauncherTestUtils;

    @Mock
    private Appender mockAppender;

    @Captor
    private ArgumentCaptor<LoggingEvent> captorLoggingEvent;

    @Test
    public void ジョブが正常終了すること() throws Exception {

        Logger root = (ch.qos.logback.classic.Logger) LoggerFactory.getLogger(UserItemProcessor.class);
        root.addAppender(mockAppender);

        final JobExecution result = jobLauncherTestUtils.launchJob();

        verify(mockAppender, times(3)).doAppend(captorLoggingEvent.capture());

        SoftAssertions.assertSoftly(softly -> {
            softly
                    .assertThat(captorLoggingEvent
                            .getAllValues())
                    .extracting("message")
                    .containsExactlyInAnyOrder("column name：userId, error message：値は8文字以上 20文字以下にしてください",
                            "column name：userId, error message：値は8文字以上 20文字以下にしてください",
                            "column name：userId, error message：必須です");
            softly
                    .assertThat(result.getStatus())
                    .isEqualTo(BatchStatus.COMPLETED);

        });
    }

    @Configuration
    @EnableBatchProcessing
    static class BatchTestConfig {

        @Bean
        JobLauncherTestUtils jobLauncherTestUtils() {
            return new JobLauncherTestUtils();
        }
    }
}
