package star16m.bootsample.resource.config;

import org.springframework.boot.autoconfigure.web.servlet.WebMvcRegistrations;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.AnnotatedElementUtils;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.http.HttpMethod;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.mvc.condition.RequestCondition;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;
import star16m.bootsample.resource.web.controller.annotations.ApiVersion;
import star16m.bootsample.resource.web.controller.annotations.SimpleRestController;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicBoolean;
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
        return new ApiAwareRequestMappingHandlerMapping();
    }

    private static class ApiAwareRequestMappingHandlerMapping extends RequestMappingHandlerMapping {

        @Override
        protected void registerHandlerMethod(Object handler, Method method, RequestMappingInfo mapping) {
            Class<?> handlerType = handler instanceof String ? this.obtainApplicationContext().getType((String)handler) : handler.getClass();
            SimpleRestController restController = AnnotatedElementUtils.findMergedAnnotation(handlerType, SimpleRestController.class);
            if (!Objects.isNull(restController)) {
                RequestMapping requestMapping = AnnotatedElementUtils.findMergedAnnotation(method, RequestMapping.class);
                AtomicBoolean isSupported = new AtomicBoolean(true);
                System.out.println("this is supported method " + method + ", controller" + restController);
//                Arrays.asList(restController.supportedMethod()).stream().filter(m -> {
//////                    isSupported.set(false);
//////                });
                if (handler.toString().contains("team") && method.getName().contains("findAll")) {
                    isSupported.set(false);
                }
                if (!isSupported.get()) {
                    return;
                }
            }
            super.registerHandlerMethod(handler, method, mapping);
        }

        @Override
        protected RequestCondition<ApiVersionRequestCondition> getCustomTypeCondition(Class<?> handlerType) {
            ApiVersion apiVersion = AnnotationUtils.findAnnotation(handlerType, ApiVersion.class);
            return createCondition(apiVersion);
        }

        @Override
        protected RequestCondition<ApiVersionRequestCondition> getCustomMethodCondition(Method method) {
            ApiVersion apiVersion = AnnotationUtils.findAnnotation(method, ApiVersion.class);
            return createCondition(apiVersion);
        }

        private RequestCondition<ApiVersionRequestCondition> createCondition(ApiVersion apiVersion) {
            return apiVersion == null ? null : new ApiVersionRequestCondition(apiVersion.value());
        }
    }
}