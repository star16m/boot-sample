package star16m.bootsample.web.config.security.jwt;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.*;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import star16m.bootsample.core.utils.SimpleUtil;
import star16m.bootsample.web.common.SimpleResponse;
import star16m.bootsample.web.config.security.resource.dto.AuthenticationRequest;
import star16m.bootsample.web.config.security.resource.dto.AuthenticationResult;
import star16m.bootsample.web.config.security.SimpleAuthenticationException;
import star16m.bootsample.web.config.security.resource.User;
import star16m.bootsample.web.config.security.resource.UserRepository;
import star16m.bootsample.web.model.ResultCode;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.Date;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@Data
@Component
@Slf4j
public class JWTAuthenticationManager implements AuthenticationFailureHandler, AuthenticationSuccessHandler, AccessDeniedHandler {
    private static final String JWT_AUTHORITY_KEY = "jwt-authority";
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ObjectMapper objectMapper;
    @Value("${app.security.header.key:Authorization}")
    private String headerKey;
    @Value("${app.security.header.prefix:Bearer }")
    private String headerPrefix;
    @Value("${app.security.signkey:simpleSignKey}")
    private String securitySignKey;
    @Value("${app.security.token.expiration.min:30}")
    private Long expirationMin;
    @Value("${app.security.url:/api/rest}")
    private String securityURIPrefix;

    public boolean isSecurityResource(HttpServletRequest request) {
        String requestURI = request.getRequestURI();
        SimpleUtil.mustNotNull(requestURI);
        return requestURI.startsWith(securityURIPrefix);
    }
    public AuthenticationRequest readAuthenticationRequestFromRequestBody(HttpServletRequest httpServletRequest) {
        try {
            return this.objectMapper.readValue(httpServletRequest.getInputStream(), AuthenticationRequest.class);
        } catch (IOException e) {
            return null;
        }
    }

    public String createToken(User user) {
        return Jwts.builder()
                .setSubject(user.getUserId())
                .claim(JWT_AUTHORITY_KEY, user.getPrivilege())
                .setExpiration(new Date(System.currentTimeMillis() + TimeUnit.MINUTES.toMillis(expirationMin)))
                .signWith(SignatureAlgorithm.HS512, this.getSecuritySignKey().getBytes())
                .compact();
    }

    public JWTAuthenticationToken createAuthenticationToken(HttpServletRequest request) {
        try {
            String parsedToken = request.getHeader(this.headerKey);
            Claims claims = Jwts.parser().setSigningKey(this.securitySignKey.getBytes())
                    .parseClaimsJws(parsedToken.replace(this.headerPrefix, "").trim())
                    .getBody();
            String userId = claims.getSubject();
            String authorities = (String)claims.get(JWT_AUTHORITY_KEY);
            if (SimpleUtil.isEmpty(userId) || SimpleUtil.isEmpty(authorities)) {
                throw new SimpleAuthenticationException(ResultCode.INVALID_TOKEN);
            }
            JWTAuthenticationToken authenticationToken = new JWTAuthenticationToken(parsedToken, userId, Arrays.asList(authorities.split(",")).stream().map(SimpleGrantedAuthority::new).collect(Collectors.toList()));
            return authenticationToken;
        } catch (ExpiredJwtException e) {
            throw new SimpleAuthenticationException(ResultCode.EXPIRED_TOKEN);
        } catch (JwtException e) {
            log.error("Error occurred while parse jwt token.", e);
            throw new SimpleAuthenticationException(ResultCode.INVALID_TOKEN);
        }
    }

    public void writeResponse(HttpServletResponse response, ResultCode resultCode) {
        writeResponse(response, null, resultCode);
    }
    public <T> void writeResponse(HttpServletResponse response, T body, ResultCode resultCode) {
        SimpleResponse<T> simpleResponse = SimpleResponse.of(body, resultCode);
        response.setContentType(MediaType.APPLICATION_JSON_UTF8_VALUE);
        try {
            byte[] bytes = objectMapper.writeValueAsString(simpleResponse).getBytes();
            response.setContentLength(bytes.length);
            ServletOutputStream outputStream = response.getOutputStream();
            outputStream.write(bytes);
            response.flushBuffer();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Authentication authentication) throws IOException, ServletException {
        org.springframework.security.core.userdetails.User principal = (org.springframework.security.core.userdetails.User) authentication.getPrincipal();
        User user = this.userRepository.findByUserId(principal.getUsername());
        String token = createToken(user);
        httpServletResponse.addHeader(this.headerKey, this.headerPrefix + token);
        AuthenticationResult result = AuthenticationResult.builder().user(user).token(token).build();
        writeResponse(httpServletResponse, result, ResultCode.SUCCESS);
    }

    @Override
    public void onAuthenticationFailure(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AuthenticationException exception) throws IOException, ServletException {
        if (exception instanceof SimpleAuthenticationException) {
            writeResponse(httpServletResponse, ((SimpleAuthenticationException) exception).getResultCode());
        } else {
            writeResponse(httpServletResponse, ResultCode.AUTHENTICATE_ERROR);
        }
    }

    @Override
    public void handle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AccessDeniedException exception) throws IOException, ServletException {
        log.warn("Access denied error occurred while request resource", exception);
        writeResponse(httpServletResponse, ResultCode.AUTHORITY_ERROR);
    }
}
