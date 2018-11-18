package star16m.bootsample.resource.config;

import org.springframework.boot.autoconfigure.web.servlet.WebMvcRegistrations;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import java.util.Arrays;
import java.util.stream.Collectors;

@Configuration
public class WebMvcConfiguration implements WebMvcConfigurer, WebMvcRegistrations {
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/api/**")
                .allowedOrigins("*")
                .allowedMethods(Arrays.asList(HttpMethod.values()).stream().map(HttpMethod::name).collect(Collectors.toList()).toArray(new String[0]))
                .allowCredentials(false)
                .maxAge(3600);
    }

    @Override
    public RequestMappingHandlerMapping getRequestMappingHandlerMapping() {
        return new CommonRequestMappingHandlerMapping();
    }
}