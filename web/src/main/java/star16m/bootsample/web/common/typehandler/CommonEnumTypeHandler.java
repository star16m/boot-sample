package star16m.bootsample.web.common.typehandler;

import star16m.bootsample.web.resource.sample.ScheduleType;

public class CommonEnumTypeHandler {
    public static class ScheduleTypeHandler extends BaseEnumTypeHandler<ScheduleType> {
        public ScheduleTypeHandler() {
            super(ScheduleType.class, "en_schedule_type");
        }
    }
}
