package star16m.bootsample.web.config.security.jwt;

import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import star16m.bootsample.core.utils.SimpleUtil;

import java.util.Collection;

public class JWTAuthenticationToken extends AbstractAuthenticationToken {

    // if need
    private String token;
    // if need
    private String userId;

    public JWTAuthenticationToken(String token, String userId, Collection<? extends GrantedAuthority> authorities) {
        super(authorities);
        SimpleUtil.mustNotNull(token);
        SimpleUtil.mustNotNull(userId);
        SimpleUtil.mustNotNull(authorities);
        this.token = token;
        this.userId = userId;
    }

    public String getToken() {
        return this.token;
    }
    @Override
    public Object getCredentials() {
        return null;
    }

    @Override
    public Object getPrincipal() {
        return userId;
    }

    @Override
    public boolean isAuthenticated() {
        return true;
    }

}
