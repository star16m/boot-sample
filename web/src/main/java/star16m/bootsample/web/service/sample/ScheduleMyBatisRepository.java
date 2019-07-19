package star16m.bootsample.web.service.sample;


import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import star16m.bootsample.web.resource.sample.Schedule;
import star16m.bootsample.web.service.repository.BaseMyBatisRepository;

import java.util.List;

@Mapper
public interface ScheduleMyBatisRepository extends BaseMyBatisRepository<Schedule, Integer> {
    @Select("select * from tb_schedule")
    List<Schedule> findAll();

    @Select("select * from tb_schedule where id = ${id}")
    Schedule findById(@Param("id") Integer id);

    Schedule save(Schedule resource);

    void deleteById(Integer id);
}
