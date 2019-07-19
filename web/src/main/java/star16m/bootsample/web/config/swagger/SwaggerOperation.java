package star16m.bootsample.web.config.swagger;


import star16m.bootsample.web.model.ResultCode;
import star16m.bootsample.web.model.ResultMessage;

import java.lang.annotation.*;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface SwaggerOperation {
    ResultMessage[] value() default { @ResultMessage(code = ResultCode.SUCCESS, description = "성공") };
    String name() default "";
}
