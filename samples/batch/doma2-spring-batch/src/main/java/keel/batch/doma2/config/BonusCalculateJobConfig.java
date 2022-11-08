package keel.batch.doma2.config;

import keel.batch.doma2.entity.Bonus;
import keel.batch.doma2.entity.EmployeeBonus;
import keel.batch.doma2.processor.EmployeeBonusProcessor;
import keel.batch.doma2.reader.EmployeeBonusReader;
import keel.batch.doma2.writer.BonusWriter;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.jsr.JsrJobParametersConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

@Configuration
public class BonusCalculateJobConfig {

    private final JobBuilderFactory jobBuilderFactory;

    private final StepBuilderFactory stepBuilderFactory;

    public BonusCalculateJobConfig(JobBuilderFactory jobBuilderFactory, StepBuilderFactory stepBuilderFactory) {
        this.jobBuilderFactory = jobBuilderFactory;
        this.stepBuilderFactory = stepBuilderFactory;
    }

    @Bean
    public Job bonusCalculateJob(Step bonusCalculateStep) {
        return jobBuilderFactory.get("bonusCalculateJob")
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
        return stepBuilderFactory.get("bonusCalculateStep")
                .<EmployeeBonus, Bonus>chunk(10)
                .reader(reader)
                .processor(processor)
                .writer(writer)
                .build();
    }
}
