package star16m.bootsample.web.service.sample;


import org.apache.ibatis.annotations.*;
import org.apache.ibatis.jdbc.SQL;
import star16m.bootsample.web.resource.sample.Schedule;
import star16m.bootsample.web.service.repository.BaseMyBatisRepository;

import java.util.List;

@Mapper
public interface ScheduleMyBatisRepository extends BaseMyBatisRepository<Schedule, Integer> {
    @Select("select * from tb_schedule")
    List<Schedule> findAll();

    @SelectProvider(type = ScheduleFilter.class, method = "filter")
    List<Schedule> findScheduleWithDynamicSQL();

    @Select("select * from tb_schedule where id = ${id}")
    Schedule findById(@Param("id") Integer id);

    @Select("insert into tb_schedule(id, schedule_type, detail, create_date) values (${id}, #{scheduleType}, #{detail}, now()) returning *")
    Schedule save(Schedule resource);

    @Delete("delete from tb_schedule where id = ${id}")
    void deleteById(@Param("id") Integer id);

    class ScheduleFilter {
        public String filter() {
            return new SQL() {{
                SELECT("id", "schedule_type", "detail", "create_date");
                FROM("tb_schedule");
                WHERE("schedule_type = 'ONCE'");
            }}.toString();
        }
    }
}
