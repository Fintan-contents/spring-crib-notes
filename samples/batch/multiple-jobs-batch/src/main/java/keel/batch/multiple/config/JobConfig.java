package keel.batch.multiple.config;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

@Configuration
public class JobConfig {

    private final JobRepository jobRepository;

    private final PlatformTransactionManager platformTransactionManager;

    public JobConfig(JobRepository jobRepository, PlatformTransactionManager platformTransactionManager) {
        this.jobRepository = jobRepository;
        this.platformTransactionManager = platformTransactionManager;
    }

    @Bean("job1")
    public Job job1() {
        return new JobBuilder("job1", jobRepository)
                .start(new StepBuilder("step", jobRepository)
                        .tasklet((contribution, chunkContext) -> {
                            System.out.println("job1 completed.");
                            return RepeatStatus.FINISHED;
                        }, platformTransactionManager)
                        .build())
                .build();
    }

    @Bean("job2")
    public Job job2() {
        return new JobBuilder("job2", jobRepository)
                .flow(new StepBuilder("step", jobRepository)
                        .tasklet((contribution, chunkContext) -> {
                            System.out.println("job2 completed.");
                            return RepeatStatus.FINISHED;
                        }, platformTransactionManager)
                        .build())
                .end()
                .build();
    }
}
