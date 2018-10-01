package star16m.bootsample.resource.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Data
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)

@Entity
@Table(name = "tb_team")
public class Team extends AbstractEntity {

    @Column(name = "full_name")
    private String fullName;
    @Column(name = "short_name")
    private String shortName;
}
