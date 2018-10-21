package star16m.bootsample.resource.config;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Map;

@EnableBatchProcessing
@Configuration
@RequiredArgsConstructor
public class BatchConfiguration {
    private final Logger log = LoggerFactory.getLogger(BatchConfiguration.class);
    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;

    @Bean
    public Job simpleJob() {
        return jobBuilderFactory.get("simpleJob")
                .start(simpleStep1())
                .incrementer(new RunIdIncrementer())
                .build();
    }

    public Step simpleStep1() {
        return stepBuilderFactory.get("simpleStep1")
                .tasklet((contribution, chunkContext) -> {
                    Map<String, Object> parameters = chunkContext.getStepContext().getJobParameters();
                    parameters.keySet().forEach(k -> System.out.println(String.format("key[{}], value[{}]", k, parameters.get(k))));
                    return RepeatStatus.FINISHED;
                })
                .build();
    }
}
