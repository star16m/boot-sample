package star16m.bootsample.resource.web.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;
import star16m.bootsample.resource.service.error.SimpleException;

@RestController
public class SchedulerController {
    private static final Logger log = LoggerFactory.getLogger(SchedulerController.class);
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
