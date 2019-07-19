package star16m.bootsample.web.controller.batch;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.batch.core.*;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import star16m.bootsample.core.error.SimpleException;
import star16m.bootsample.web.controller.annotations.SimpleRestController;
import star16m.bootsample.web.service.action.ActionRequest;

import javax.validation.Valid;

@SimpleRestController(path="/api/rest/v1/batch")
@Api(tags = "Batch")
public class BatchActionController {

    @Autowired
    private JobLauncher jobLauncher;
    @Autowired
    private Job actionJob;

    @PostMapping("action")
    @ApiOperation(value = "전체 Batch 조회")
    public String execute(@RequestBody @Valid ActionRequest actionRequest) {

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
