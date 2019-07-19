package star16m.bootsample.web.resource.sample;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import star16m.bootsample.web.resource.BaseResource;

import javax.persistence.*;

@Data
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "tb_schedule")
public class Schedule extends BaseResource<Integer> {
    @Id
    @Column(name = "id")
    @ApiModelProperty(notes = "스케줄 key ID", example = "1")
    private Integer id;

    @Column(name = "schedule_type")
    @Enumerated(EnumType.STRING)
    @ApiModelProperty(notes = "스케줄 타입", example = "ONCE")
    private ScheduleType scheduleType;

    @Column(name = "detail")
    @ApiModelProperty(notes = "스케줄 상세", example = "상세 설명입니다.")
    private String detail;

    @Override
    public Integer getId() {
        return this.id;
    }
}
