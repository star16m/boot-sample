package star16m.bootsample.web.service.sample;

import org.springframework.stereotype.Component;
import star16m.bootsample.web.resource.sample.Schedule;
import star16m.bootsample.web.service.BaseJpaService;
import star16m.bootsample.web.service.BaseMyBatisService;
import star16m.bootsample.web.service.BaseService;

import java.util.Map;

@Component
public class ScheduleMyBatisService extends BaseMyBatisService<Schedule, Integer> implements BaseService<Schedule, Integer> {

    public ScheduleMyBatisService(ScheduleMyBatisRepository scheduleMyBatisRepository) {
        super(scheduleMyBatisRepository);
    }

    @Override
    public void patchedObject(Schedule schedule, Map<String, Object> map) {
        // do nothing.
    }
}
