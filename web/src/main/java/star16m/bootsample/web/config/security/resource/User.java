package star16m.bootsample.web.config.security.resource;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Entity
@Getter
@Setter
@Table(name = "tb_user")
public class User {
    @Id
    @NotEmpty
    @Size(min=4, max=30)
    @Pattern(regexp="^[\\w@.]+$", message = "{validation.id}")
    private String userId;
    @NotEmpty
    @Pattern(regexp="(^$|.{4,255})", message = "{validation.password}")
    @JsonIgnore
    private String password;
    private String privilege;
    @NotEmpty @Size(min=2, max=30)
    private String name;
    @NotEmpty @Size(min=5, max=30)
    @Pattern(regexp="\\d{10,11}|\\d{2,3}-\\d{3,4}-\\d{4}|", message = "{validation.tel}")
    private String tel;
    @NotEmpty @Size(min=3, max=255)
    @Pattern(regexp="^[_a-z0-9-]+(.[_a-z0-9-]+)*@(?:\\w+\\.)+\\w+$", message = "{validation.email}")
    private String email;
    @NotEmpty @Size(max=255)
    private String description;
}