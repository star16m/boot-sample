package star16m.bootsample.resource.config;


import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface SwaggerOperation {
    ResultMessage[] value() default { @ResultMessage(code = ResultCode.SUCCESS, description = "성공") };
}
