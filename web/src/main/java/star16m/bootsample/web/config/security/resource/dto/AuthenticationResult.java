package star16m.bootsample.web.config.security.resource.dto;

import lombok.Builder;
import lombok.Data;
import star16m.bootsample.web.config.security.resource.User;

@Data
@Builder
public class AuthenticationResult {
    private User user;
    private String token;
}
