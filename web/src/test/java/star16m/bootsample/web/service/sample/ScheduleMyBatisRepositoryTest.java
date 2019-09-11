package star16m.bootsample.web.service.sample;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;
import star16m.bootsample.web.SampleApplication;
import star16m.bootsample.web.resource.sample.Schedule;
import star16m.bootsample.web.resource.sample.ScheduleType;

import java.util.List;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;

//@RunWith(SpringRunner.class)
@ExtendWith(SpringExtension.class)
@SpringBootTest(classes= SampleApplication.class, webEnvironment = SpringBootTest.WebEnvironment.NONE)
@Transactional
//if using MybatisTest annotation.
//@RunWith(SpringRunner.class)
//@MybatisTest
//@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class ScheduleMyBatisRepositoryTest {

    @Autowired
    private ScheduleMyBatisRepository scheduleMyBatisRepository;

    @BeforeEach
    public void setUp() throws Exception {
    }

    @AfterEach
    public void tearDown() throws Exception {
    }

    @Test
    public void findAll() {
        List<Schedule> scheduleList = scheduleMyBatisRepository.findAll();
        assertThat(scheduleList).isNotEmpty().hasSize(1);
    }

    @Test
    public void findById() {
        Schedule schedule = scheduleMyBatisRepository.findById(1);
        assertThat(schedule).isNotNull().extracting(Schedule::getId).isEqualTo(1);
    }

    @Test
    public void save() {
        Schedule schedule = new Schedule();
        schedule.setId(2);
        schedule.setScheduleType(ScheduleType.ONCE);
        schedule.setDetail("this is test");
        Schedule newSchedule = this.scheduleMyBatisRepository.save(schedule);
        assertThat(newSchedule).isNotNull().extracting(Schedule::getId).isEqualTo(2);
        assertThat(newSchedule).isNotNull().extracting(Schedule::getScheduleType).isEqualTo(ScheduleType.ONCE);
    }

    @Test
    public void deleteById() {
        List<Schedule> scheduleList = this.scheduleMyBatisRepository.findAll();
        assertThat(scheduleList).isNotNull().isNotEmpty().hasSize(1);
        this.scheduleMyBatisRepository.deleteById(1);
        scheduleList = this.scheduleMyBatisRepository.findAll();
        assertThat(scheduleList).isNotNull().isEmpty();
    }

    @Test
    public void filter() {
        List<Schedule> schedule = this.scheduleMyBatisRepository.findScheduleWithDynamicSQL();
        assertThat(schedule).isNotNull().isNotEmpty().hasSize(1);
    }
}