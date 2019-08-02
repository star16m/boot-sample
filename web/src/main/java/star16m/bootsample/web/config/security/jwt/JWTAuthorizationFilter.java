package star16m.bootsample.web.config.security.jwt;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import star16m.bootsample.core.utils.SimpleUtil;
import star16m.bootsample.web.config.security.SimpleAuthenticationException;
import star16m.bootsample.web.model.ResultCode;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class JWTAuthorizationFilter extends BasicAuthenticationFilter {

    private JWTAuthenticationManager jwtAuthenticationManager;

    public JWTAuthorizationFilter(AuthenticationManager authenticationManager, JWTAuthenticationManager jwtAuthenticationManager) {
        super(authenticationManager);
        this.jwtAuthenticationManager = jwtAuthenticationManager;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain chain) throws IOException, ServletException {
        if (!this.jwtAuthenticationManager.isSecurityResource(request)) {
            chain.doFilter(request, response);
            return;
        }
        // check authorize for resource
        JWTAuthenticationToken authentication;
        try {
            authentication = getAuthentication(request);
            SecurityContextHolder.getContext().setAuthentication(authentication);
        } catch (SimpleAuthenticationException e) {
            this.jwtAuthenticationManager.onAuthenticationFailure(request, response, e);
            return;
        }
        chain.doFilter(request, response);
    }

    private JWTAuthenticationToken getAuthentication(HttpServletRequest request) {
        JWTAuthenticationToken authenticationToken = this.jwtAuthenticationManager.createAuthenticationToken(request);
        if (SimpleUtil.isNull(authenticationToken)) {
            throw new SimpleAuthenticationException(ResultCode.INVALID_TOKEN);
        }
        return authenticationToken;
    }
}
