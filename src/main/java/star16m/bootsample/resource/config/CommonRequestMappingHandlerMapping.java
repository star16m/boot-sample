package star16m.bootsample.resource.config;

import org.springframework.core.annotation.AnnotatedElementUtils;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;
import star16m.bootsample.resource.utils.SimpleUtil;
import star16m.bootsample.resource.web.controller.annotations.SimpleRestController;
import star16m.bootsample.resource.web.controller.annotations.SimpleRestMethod;
import star16m.bootsample.resource.web.controller.annotations.SimpleRestMethodMapping;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Optional;

public class CommonRequestMappingHandlerMapping extends RequestMappingHandlerMapping {

    @Override
    protected void registerHandlerMethod(Object handler, Method method, RequestMappingInfo mapping) {
        SimpleRestMethodMapping methodMapping = AnnotatedElementUtils.findMergedAnnotation(method, SimpleRestMethodMapping.class);
        if (SimpleUtil.isNotNull(methodMapping)) {
            Class<?> handlerType = handler instanceof String ? this.obtainApplicationContext().getType((String)handler) : handler.getClass();
            SimpleRestController restController = AnnotatedElementUtils.findMergedAnnotation(handlerType, SimpleRestController.class);
            if (SimpleUtil.isNotNull(restController)) {
                Optional<SimpleRestMethod> supportMethods = Arrays.asList(restController.supportedMethod()).stream().filter(supportMethod -> supportMethod.equals(methodMapping.value())).findAny();
                if (!supportMethods.isPresent()) {
                    return;
                }
            }
        }
        super.registerHandlerMethod(handler, method, mapping);
    }
}
