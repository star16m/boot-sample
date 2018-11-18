package star16m.bootsample.resource.entity;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Data
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "tb_player")
public class Player extends BaseEntity<Integer> {
    @Id
    @Column(name = "id")
    @ApiModelProperty(notes = "선수의 key ID", example = "1")
    private Integer id;

    @Column(name = "name")
    @ApiModelProperty(notes = "선수 이름", example = "이대호")
    private String name;

    @Column(name = "back_number")
    @ApiModelProperty(notes = "선수 백넘버", example = "07")
    private String backNumber;
}
