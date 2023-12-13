package keel.batch.doma2.config;

import keel.batch.doma2.entity.Bonus;
import keel.batch.doma2.entity.EmployeeBonus;
import keel.batch.doma2.jsr.JsrJobParametersConverter;
import keel.batch.doma2.processor.EmployeeBonusProcessor;
import keel.batch.doma2.reader.EmployeeBonusReader;
import keel.batch.doma2.writer.BonusWriter;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;

@Configuration
public class BonusCalculateJobConfig {

    private final JobRepository jobRepository;

    private final PlatformTransactionManager platformTransactionManager;

    public BonusCalculateJobConfig(JobRepository jobRepository, PlatformTransactionManager platformTransactionManager) {
        this.jobRepository = jobRepository;
        this.platformTransactionManager = platformTransactionManager;
    }

    @Bean
    public Job bonusCalculateJob(Step bonusCalculateStep) {
        return new JobBuilder("bonusCalculateJob", jobRepository)
                                .start(bonusCalculateStep)
                                .build();
    }

    // job-incrementer-start
    @Bean
    public JsrJobParametersConverter jsrJobParametersConverter(DataSource dataSource) {
        // 起動時に一意のパラメータ(jsr_batch_run_id)を設定する
        return new JsrJobParametersConverter(dataSource);
    }
    // job-incrementer-end

    @Bean
    public Step bonusCalculateStep(EmployeeBonusReader reader, EmployeeBonusProcessor processor, BonusWriter writer) {
        return new StepBuilder("bonusCalculateStep", jobRepository)
                .<EmployeeBonus, Bonus>chunk(10, platformTransactionManager)
                .reader(reader)
                .processor(processor)
                .writer(writer)
                .build();
    }
}
