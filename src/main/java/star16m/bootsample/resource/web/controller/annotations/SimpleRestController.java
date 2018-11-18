package star16m.bootsample.resource.web.controller.annotations;

import org.springframework.core.annotation.AliasFor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@RestController
@RequestMapping(value = "")
public @interface SimpleRestController {
    @AliasFor(annotation = RequestMapping.class)
    String path();

    SimpleRestMethod[] supportedMethod() default {
            SimpleRestMethod.FIND_ALL,
            SimpleRestMethod.FIND_ONE,
            SimpleRestMethod.CREATE,
            SimpleRestMethod.DELETE,
            SimpleRestMethod.UPDATE,
    };
}
