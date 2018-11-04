package star16m.bootsample.resource.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.*;
import org.springframework.batch.core.explore.JobExplorer;
import org.springframework.batch.core.explore.support.MapJobExplorerFactoryBean;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.launch.support.SimpleJobLauncher;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.repository.support.MapJobRepositoryFactoryBean;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.database.JdbcBatchItemWriter;
import org.springframework.batch.item.database.JdbcCursorItemReader;
import org.springframework.batch.item.database.builder.JdbcBatchItemWriterBuilder;
import org.springframework.batch.item.database.builder.JdbcCursorItemReaderBuilder;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.FlatFileItemWriter;
import org.springframework.batch.item.file.builder.FlatFileItemReaderBuilder;
import org.springframework.batch.item.file.builder.FlatFileItemWriterBuilder;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.transform.DelimitedLineAggregator;
import org.springframework.batch.item.file.transform.PassThroughFieldExtractor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.core.task.SimpleAsyncTaskExecutor;
import org.springframework.jdbc.core.ColumnMapRowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import star16m.bootsample.resource.service.action.Action;
import star16m.bootsample.resource.service.action.ActionProvider;
import star16m.bootsample.resource.service.action.ActionType;
import star16m.bootsample.resource.service.error.EntityNotfoundException;

import javax.sql.DataSource;
import java.nio.charset.Charset;
import java.util.Map;
import java.util.stream.Collectors;

@Configuration
@EnableBatchProcessing
public class BatchConfiguration {
    private final Logger log = LoggerFactory.getLogger(BatchConfiguration.class);
    private final DataSource dataSource;
    private final NamedParameterJdbcTemplate jdbcTemplate;
    private final ActionProvider actionProvider;
    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;
    private final int chunkSize;

    public BatchConfiguration(DataSource dataSource, NamedParameterJdbcTemplate jdbcTemplate, ActionProvider actionProvider, JobBuilderFactory jobBuilderFactory, StepBuilderFactory stepBuilderFactory, @Value("${spring.batch.chunksize:100}") int chunkSize) {
        this.dataSource = dataSource;
        this.jdbcTemplate = jdbcTemplate;
        this.actionProvider = actionProvider;
        this.jobBuilderFactory = jobBuilderFactory;
        this.stepBuilderFactory = stepBuilderFactory;
        this.chunkSize = chunkSize;
    }
    @Bean
    public DefaultBatchConfigurer batchConfigurer() {
        return new DefaultBatchConfigurer() {

            private JobRepository jobRepository;
            private JobExplorer jobExplorer;
            private JobLauncher jobLauncher;

            {
                MapJobRepositoryFactoryBean jobRepositoryFactory = new MapJobRepositoryFactoryBean();
                try {
                    this.jobRepository = jobRepositoryFactory.getObject();
                    MapJobExplorerFactoryBean jobExplorerFactory = new MapJobExplorerFactoryBean(jobRepositoryFactory);
                    this.jobExplorer = jobExplorerFactory.getObject();

                    SimpleJobLauncher jobLauncher = new SimpleJobLauncher();
                    jobLauncher.setJobRepository(jobRepository);
                    SimpleAsyncTaskExecutor taskExecutor = new SimpleAsyncTaskExecutor();
                    taskExecutor.setConcurrencyLimit(3);
                    jobLauncher.setTaskExecutor(taskExecutor);
                    // jobLauncher.afterPropertiesSet();
                    this.jobLauncher = jobLauncher;

                } catch (Exception e) {
                }
            }

            @Override
            public JobRepository getJobRepository() {
                return jobRepository;
            }

            @Override
            public JobExplorer getJobExplorer() {
                return jobExplorer;
            }

            @Override
            public JobLauncher getJobLauncher() {
                return jobLauncher;
            }
        };
    }
    @Bean
    public Job simpleJob(NamedParameterJdbcTemplate jdbcTemplate) {
        return jobBuilderFactory.get("actionJob")
                .start(actionStep(null))
                .incrementer(new RunIdIncrementer())
                .build();
    }
    @Bean
    @JobScope
    public Step actionStep(@Value("#{jobParameters[actionName]}") String actionName) {
        Action action = this.actionProvider.getAction(actionName).orElseThrow(() -> new EntityNotfoundException(actionName));
        log.debug("action step as [{}]", action);
        ItemReader<Map<String, Object>> reader = null;
        switch (action.getReadType()) {
            case csv: reader = readerWithFile(actionName); break;
            case db : reader = readerWithDb(actionName); break;
            default:
        }
        ItemWriter<Map<String, Object>> writer = null;
        switch (action.getWriteType()) {
            case csv:
                writer = writerWithFile(actionName);
                break;
            case db:
                writer = writerWithJdbc(actionName);
                break;
            default:
        }
        return stepBuilderFactory
                .get("actionStep")
                .<Map<String, Object>, Map<String, Object>>chunk(this.chunkSize)
                .reader(reader)
                .writer(writer)
                .build();
    }
    @Bean(destroyMethod="")
    @StepScope
    public JdbcCursorItemReader<Map<String, Object>> readerWithDb(@Value("#{jobParameters[actionName]}") String actionName) {
        Action action = this.actionProvider.getAction(actionName).orElseThrow(() -> new EntityNotfoundException(actionName));
        return new JdbcCursorItemReaderBuilder<Map<String, Object>>()
                .fetchSize(this.chunkSize)
                .dataSource(this.dataSource)
                .sql(action.getReadDetail())
                .rowMapper(new ColumnMapRowMapper())
                .name("actionItemWriterWithJdbc")
                .build();
    }

    @Bean
    @StepScope
    public JdbcBatchItemWriter<Map<String, Object>> writerWithJdbc(@Value("#{jobParameters[actionName]}") String actionName) {
        Action action = this.actionProvider.getAction(actionName).orElseThrow(() -> new EntityNotfoundException(actionName));
        return new JdbcBatchItemWriterBuilder<Map<String, Object>>()
                .columnMapped()
                .dataSource(dataSource)
                .sql(action.getWriteDetail())
                .build();
    }

    @Bean
    @StepScope
    public FlatFileItemWriter<Map<String, Object>> writerWithFile(@Value("#{jobParameters[actionName]}") String actionName) {
        Action action = this.actionProvider.getAction(actionName).orElseThrow(() -> new EntityNotfoundException(actionName));
        return new FlatFileItemWriterBuilder<Map<String, Object>>()
                .name("actionItemWriterWithFile")
                .resource(new FileSystemResource(action.getWriteDetail()))
                .encoding("UTF-8")
                .shouldDeleteIfExists(true)
                .lineAggregator(new DelimitedLineAggregator<Map<String, Object>>() {
                    {
                        setDelimiter(",");
                        // setFieldExtractor(map -> map.keySet().stream().map(k -> map.get(k)).collect(Collectors.toList()).toArray());
                    }
                })
                .build();
    }

    @Bean(destroyMethod="")
    @StepScope
    public FlatFileItemReader<Map<String, Object>> readerWithFile(@Value("#{jobParameters[actionName]}") String actionName) {
        Action action = this.actionProvider.getAction(actionName).orElseThrow(() -> new EntityNotfoundException(actionName));
        return new FlatFileItemReaderBuilder<Map<String, Object>>()
                .name("actionItemReaderWithFile")
                .resource(new FileSystemResource(action.getReadDetail()))
                .encoding("UTF-8")
                .fieldSetMapper(new BeanWrapperFieldSetMapper<>())
                .delimited().delimiter(",").names(action.getColumns())
                .build();
    }
}
