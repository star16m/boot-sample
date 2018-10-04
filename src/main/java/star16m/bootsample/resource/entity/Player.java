package star16m.bootsample.resource.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.*;

@Data
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)

@Entity
@Table(name = "tb_player")
public class Player extends AbstractEntity<Integer> {
    @Id
    @Column(name = "id")
    private Integer id;
    @Column(name = "back_number")
    private Integer backNumber;
}
