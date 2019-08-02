package star16m.bootsample.web.config.security.resource.dto;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Data
public class AuthenticationRequest {

    @NotEmpty
    @Size(min=4, max=30)
    private String userId;
    @NotEmpty
    @Pattern(regexp="(^$|.{4,255})", message = "{validation.password}")
    private String password;
}
