package star16m.bootsample.resource.web.controller;

import org.springframework.batch.core.*;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import star16m.bootsample.resource.service.action.ActionRequest;
import star16m.bootsample.resource.service.error.SimpleException;

import javax.validation.Valid;

@RestController("/batch")
public class BatchActionController {

    private final JobLauncher jobLauncher;
    private final Job actionJob;
    public BatchActionController(JobLauncher jobLauncher, Job actionJob) {
        this.jobLauncher = jobLauncher;
        this.actionJob = actionJob;
    }

    @PostMapping("action")
    public String getAction(@RequestBody @Valid ActionRequest actionRequest) {

        JobParameters jobParameters = new JobParametersBuilder()
                .addString("actionName", actionRequest.getActionName(), true)
                .addLong("time", System.nanoTime())
                .toJobParameters();
        JobExecution run = null;
        try {
            run = jobLauncher.run(actionJob, jobParameters);
        } catch (JobExecutionAlreadyRunningException e) {
            e.printStackTrace();
            throw new SimpleException(e.getMessage());
        } catch (JobRestartException e) {
            e.printStackTrace();
            throw new SimpleException(e.getMessage());
        } catch (JobInstanceAlreadyCompleteException e) {
            e.printStackTrace();
            throw new SimpleException(e.getMessage());
        } catch (JobParametersInvalidException e) {
            e.printStackTrace();
            throw new SimpleException(e.getMessage());
        }
        return "RUNNING...";
    }
}
