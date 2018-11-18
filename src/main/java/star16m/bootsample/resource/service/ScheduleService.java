package star16m.bootsample.resource.service;

import org.springframework.stereotype.Component;
import star16m.bootsample.resource.entity.Schedule;

import java.util.Map;

@Component
public class ScheduleService extends BaseService<Schedule, Integer> {

    public ScheduleService(ScheduleRepository scheduleRepository) {
        super(scheduleRepository);
    }

    @Override
    protected void patchedObject(Schedule schedule, Map<String, Object> map) {
        // do nothing.
    }
}
