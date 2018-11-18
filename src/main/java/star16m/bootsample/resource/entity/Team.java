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
@Table(name = "tb_team")
public class Team extends BaseEntity<Integer> {

    @Id
    @Column(name = "id")
    @ApiModelProperty(notes = "팀의 key ID")
    private Integer id;
    @Column(name = "full_name")
    private String fullName;
    @Column(name = "short_name")
    private String shortName;

    @Override
    public Integer getId() {
        return id;
    }
}
