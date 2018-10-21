package star16m.bootsample.resource.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;

@Configuration
@EnableScheduling
public class SchedulerConfiguration {

    private static final Logger log = LoggerFactory.getLogger(SchedulerConfiguration.class);

    @Bean
    public ThreadPoolTaskScheduler taskScheduler(@Value("${application.scheduler.thread.name.prefix}") String taskSchedulerThreadNamePrefix,
                                                 @Value("${application.scheduler.pool.size}") int schedulerPoolSize) {
        ThreadPoolTaskScheduler taskScheduler = new ThreadPoolTaskScheduler();
        taskScheduler.setThreadNamePrefix(taskSchedulerThreadNamePrefix);
        taskScheduler.setPoolSize(schedulerPoolSize);
        log.info("Initialized task scheduler. Thread name prefix [{}], pool size [{}]", taskSchedulerThreadNamePrefix, schedulerPoolSize);
        return taskScheduler;
    }
}
