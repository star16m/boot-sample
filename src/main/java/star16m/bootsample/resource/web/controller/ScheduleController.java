package star16m.bootsample.resource.web.controller;

import io.swagger.annotations.Api;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import star16m.bootsample.resource.Resource;
import star16m.bootsample.resource.entity.Schedule;
import star16m.bootsample.resource.service.BaseService;
import star16m.bootsample.resource.service.ScheduleService;
import star16m.bootsample.resource.service.error.SimpleException;
import star16m.bootsample.resource.web.controller.annotations.SimpleRestController;
import star16m.bootsample.resource.web.controller.annotations.SimpleRestMethod;

@Api(tags = { "Schedule" })
@SimpleRestController(path="/api/rest/schedule", supportedMethod = {SimpleRestMethod.FIND_ALL, SimpleRestMethod.FIND_ONE})
public class ScheduleController extends BaseController<Schedule, Integer> {
    private static final Logger log = LoggerFactory.getLogger(ScheduleController.class);

    private ScheduleService scheduleService;
    public ScheduleController(ScheduleService scheduleService) {
        super(Resource.SCHEDULE, scheduleService);
        this.scheduleService = scheduleService;
    }
//    @AutowiredSchedulerController
//    private SchedulerService schedulerService;

//    @PostMapping("/api/rest/schedule")
//    public ResponseEntity<String> addSchedule(@RequestBody @Valid TaskInfo taskInfo) {
////        SimpleUtil.mustNotNull(taskInfo);
////        SimpleUtil.mustNotNull(taskInfo.getTaskRunnable());
//        log.info("try add schedule by {}", taskInfo);
////        this.schedulerService.schedule(taskInfo);
//        return WebUtil.response("success");
//    }

    @ExceptionHandler(SimpleException.class)
    public final org.springframework.http.ResponseEntity<String> handleSimpleException(SimpleException ex, WebRequest request) {
        return new org.springframework.http.ResponseEntity<>("Can't find scheduler info", HttpStatus.BAD_REQUEST);
    }
}
