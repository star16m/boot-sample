package star16m.bootsample;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;

@TestConfiguration
public class TestConfigurations {
    @Value("${app.swagger.security.header.key:fake-token-for-swagger}")
    private String swaggerHeaderKey;
    @Bean
    public HttpHeaders httpHeaders() {
        HttpHeaders requestHeaders = new HttpHeaders();
        requestHeaders.setContentType(MediaType.APPLICATION_JSON);
        requestHeaders.add(this.swaggerHeaderKey, "admin");
        return requestHeaders;
    }
}
