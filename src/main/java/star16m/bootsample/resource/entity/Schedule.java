package star16m.bootsample.resource.entity;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.*;

@Data
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "tb_schedule")
public class Schedule extends BaseEntity<Integer> {
    @Id
    @Column(name = "id")
    @ApiModelProperty(notes = "스케줄 key ID", example = "1")
    private Integer id;

    @Column(name = "type")
    @Enumerated(EnumType.STRING)
    @ApiModelProperty(notes = "스케줄 타입", example = "ONCE")
    private ScheduleType scheduleType;

    @Column(name = "detail")
    @ApiModelProperty(notes = "스케줄 상세", example = "{schedule=haha}")
    private String scheduleDetail;
    @Override
    public Integer getId() {
        return this.id;
    }
}
