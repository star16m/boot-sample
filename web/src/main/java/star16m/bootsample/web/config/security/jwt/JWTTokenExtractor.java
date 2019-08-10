package star16m.bootsample.web.config.security.jwt;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import star16m.bootsample.core.utils.SimpleUtil;
import star16m.bootsample.web.config.security.resource.User;
import star16m.bootsample.web.config.security.resource.UserRepository;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.concurrent.TimeUnit;

public abstract class JWTTokenExtractor {

    abstract String extract(HttpServletRequest request);

    @Component @Profile("!enabled-swagger")
    static class DefaultJWTTokenExTractor extends JWTTokenExtractor {
        @Value("${app.security.header.key:Authorization}")
        private String headerKey;
        @Override
        String extract(HttpServletRequest request) {
            return request.getHeader(headerKey);
        }
    }

    @Component @Profile("enabled-swagger")
    static class FakeJWTTokenExTractor4Swagger extends JWTTokenExtractor {
        @Autowired
        UserRepository userRepository;
        @Value("${app.security.signkey:simpleSignKey}")
        private String securitySignKey;
        @Value("${app.security.header.key:Authorization}")
        private String headerKey;
        @Value("${app.security.token.autority:jwt-authority}")
        private String tokenAuthority;
        @Value("${app.security.header.prefix:Bearer }")
        private String headerPrefix;
        @Value("${app.swagger.security.header.key:fake-token-for-swagger}")
        private String swaggerHeaderKey;
        @Override
        String extract(HttpServletRequest request) {
            String token = request.getHeader(headerKey);
            if (!SimpleUtil.isEmpty(token)) {
                return token;
            }
            String userId = request.getHeader(swaggerHeaderKey);
            if (SimpleUtil.isEmpty(userId)) {
                return null;
            }
            User user = this.userRepository.findByUserId(userId);
            if (SimpleUtil.isNull(user)) {
                return null;
            }
            token = Jwts.builder()
                    .setSubject(userId)
                    .claim(tokenAuthority, user.getPrivilege())
                    .setExpiration(new Date(System.currentTimeMillis() + TimeUnit.MINUTES.toMillis(30)))
                    .signWith(SignatureAlgorithm.HS512, securitySignKey.getBytes())
                    .compact();
            return token;
        }
    }
}
