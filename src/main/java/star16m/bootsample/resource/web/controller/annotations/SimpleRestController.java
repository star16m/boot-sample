package star16m.bootsample.resource.web.controller.annotations;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModel;
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
@RequestMapping(value = "" )
public @interface SimpleRestController {
    @AliasFor(annotation = RequestMapping.class)
    String path();
}
