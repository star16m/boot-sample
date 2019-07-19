package star16m.bootsample.web.controller.sample;

import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import star16m.bootsample.web.controller.BaseController;
import star16m.bootsample.web.controller.annotations.SimpleRestController;
import star16m.bootsample.web.resource.sample.Resource;
import star16m.bootsample.web.resource.sample.Schedule;
import star16m.bootsample.web.service.BaseService;
import star16m.bootsample.web.service.sample.ScheduleMyBatisService;

@Slf4j
@Api(tags = { "Schedule" })
@SimpleRestController(path="/api/rest/v1/schedule")
public class ScheduleController extends BaseController<Schedule, Integer> {

    private ScheduleMyBatisService scheduleService;
    public ScheduleController(ScheduleMyBatisService scheduleService) {
        super(Resource.SCHEDULE, scheduleService);
        this.scheduleService = scheduleService;
    }
}
