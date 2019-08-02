package star16m.bootsample.web.config.security.jwt;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import star16m.bootsample.core.utils.SimpleUtil;
import star16m.bootsample.web.config.security.resource.dto.AuthenticationRequest;
import star16m.bootsample.web.config.security.SimpleAuthenticationException;
import star16m.bootsample.web.config.security.resource.UserRepository;
import star16m.bootsample.web.model.ResultCode;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
public class JWTAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private JWTAuthenticationManager jwtAuthenticationManager;
    private UserRepository userRepository;

    public JWTAuthenticationFilter(JWTAuthenticationManager jwtAuthenticationManager, UserRepository userRepository) {
        this.jwtAuthenticationManager = jwtAuthenticationManager;
        this.userRepository = userRepository;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        AuthenticationRequest authenticationRequest = this.jwtAuthenticationManager.readAuthenticationRequestFromRequestBody(request);
        if (SimpleUtil.isNull(authenticationRequest)
                || SimpleUtil.isEmpty(authenticationRequest.getUserId())
                || SimpleUtil.isEmpty(authenticationRequest.getPassword())) {
            throw new SimpleAuthenticationException(ResultCode.INVALID_ARGUMNET);
        }
        return getAuthenticationManager().authenticate(new UsernamePasswordAuthenticationToken(
                authenticationRequest.getUserId(),
                authenticationRequest.getPassword())
        );
    }

    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) throws IOException, ServletException {
        getFailureHandler().onAuthenticationFailure(request, response, failed);
    }
}
