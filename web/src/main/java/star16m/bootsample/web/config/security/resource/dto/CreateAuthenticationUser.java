package star16m.bootsample.web.config.security.resource.dto;

import lombok.Data;

@Data
public class CreateAuthenticationUser {
    private String name;
    private String email;
    private String password;
}
