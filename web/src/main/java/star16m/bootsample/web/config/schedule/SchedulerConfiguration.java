package star16m.bootsample.web.config.schedule;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;

@Configuration
@EnableScheduling
@Slf4j
public class SchedulerConfiguration {

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
