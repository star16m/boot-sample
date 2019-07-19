package star16m.bootsample.web.config.swagger;

import com.google.common.base.Optional;
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
import star16m.bootsample.web.model.ResultMessage;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Component
@Order(SwaggerPluginSupport.SWAGGER_PLUGIN_ORDER + 1002)
@Slf4j
public class SwaggerOperationBuilder implements OperationBuilderPlugin {

    @Override
    public void apply(OperationContext context) {
        ResultMessage[] resultMessages = (ResultMessage[]) AnnotationUtils.getDefaultValue(SwaggerOperation.class);
        Optional<SwaggerOperation> swaggerOperation = context.findAnnotation(SwaggerOperation.class);
        if (swaggerOperation.isPresent() && swaggerOperation.get().value() != null) {
            resultMessages = swaggerOperation.get().value();
            context.operationBuilder().notes("hahhaa");
            context.operationBuilder().summary("summary lkajhsdlfkjh");
        }
        context.operationBuilder().responseMessages(getResponseMessageList(resultMessages));
    }

    @Override
    public boolean supports(DocumentationType documentationType) {
        return true;
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
