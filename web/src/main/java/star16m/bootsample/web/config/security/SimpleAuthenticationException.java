package star16m.bootsample.web.config.security;

import org.springframework.security.core.AuthenticationException;
import star16m.bootsample.web.model.ResultCode;

public class SimpleAuthenticationException extends AuthenticationException {

    private ResultCode resultCode;
    public SimpleAuthenticationException(ResultCode resultCode) {
        super(resultCode.getDescription());
        this.resultCode = resultCode;
    }

    public ResultCode getResultCode() {
        return resultCode;
    }
}
