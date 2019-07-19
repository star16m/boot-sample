package star16m.bootsample.web.config.swagger;

import com.google.common.base.Optional;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import springfox.documentation.builders.ResponseMessageBuilder;
import springfox.documentation.service.ResponseMessage;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.OperationBuilderPlugin;
import springfox.documentation.spi.service.contexts.OperationContext;
import springfox.documentation.swagger.common.SwaggerPluginSupport;
import star16m.bootsample.core.utils.SimpleUtil;
import star16m.bootsample.web.controller.annotations.SimpleRestController;
import star16m.bootsample.web.model.ResultMessage;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Component
@Order(SwaggerPluginSupport.SWAGGER_PLUGIN_ORDER + 1)
@Slf4j
public class SwaggerOperationBuilder implements OperationBuilderPlugin {

    @Override
    public void apply(OperationContext context) {
        Optional<SimpleRestController> apiInfo = context.findControllerAnnotation(SimpleRestController.class);
        Optional<SwaggerOperation> swaggerInfo = context.findAnnotation(SwaggerOperation.class);
        if (apiInfo.isPresent()) {
            SimpleRestController api = apiInfo.get();
            context.operationBuilder()
                    .notes(SimpleUtil.getString(api.summary(), context.getGroupName()))
                    .summary(SimpleUtil.getString(swaggerInfo.isPresent() ? swaggerInfo.get().name() : context.getName()))
                    ;
            log.info("apiInfo [{}]", apiInfo);
        }
        Optional<SwaggerOperation> swaggerOperation = context.findAnnotation(SwaggerOperation.class);
        if (swaggerOperation.isPresent() && swaggerOperation.get().value() != null) {
            context.operationBuilder().responseMessages(getResponseMessageList(swaggerOperation.get().value()));
        }
    }

    @Override
    public boolean supports(DocumentationType delimiter) {
        return SwaggerPluginSupport.pluginDoesApply(delimiter);
    }
    private Set<ResponseMessage> getResponseMessageList(ResultMessage[] resultMessages) {
        Set<ResponseMessage> responseMessages = new HashSet<>();
        responseMessages.add(new ResponseMessageBuilder()
                .code(200)
                .message(Arrays.asList(resultMessages).stream().map(c -> String.format("<tr><td>%s</td><td>%s</td><td>%s</td></tr>", c.code(), c.code().getCode(), SimpleUtil.getString(c.description(), c.code().getDescription()))).collect(Collectors.joining("", "<div class=\"swagger-ui\"><div class=\"opblock\"><div class=\"opblock-summary-method\">다음의 result 가 올 수 있습니다. </div></div></div><div><table><tr><th>Result</th><th>Code</th><th>Description</th></tr>", "</table><div>")))
                .build());
        return responseMessages;
    }
}
