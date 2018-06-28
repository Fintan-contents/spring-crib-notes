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
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BonusCalculateJobConfig {

    private final JobBuilderFactory jobBuilderFactory;

    private final StepBuilderFactory stepBuilderFactory;

    public BonusCalculateJobConfig(JobBuilderFactory jobBuilderFactory, StepBuilderFactory stepBuilderFactory) {
        this.jobBuilderFactory = jobBuilderFactory;
        this.stepBuilderFactory = stepBuilderFactory;
    }

    // job-incrementer-start
    @Bean
    public Job bonusCalculateJob(Step bonusCalculateStep) {
        return jobBuilderFactory.get("bonusCalculateJob")
                                // 起動時に一意のパラメータを設定する
                                .incrementer(new RunIdIncrementer())
                                .start(bonusCalculateStep)
                                .build();
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
