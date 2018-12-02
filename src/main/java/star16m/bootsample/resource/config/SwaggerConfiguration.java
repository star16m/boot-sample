package star16m.bootsample.resource.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.RequestMethod;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.builders.ResponseMessageBuilder;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.ResponseMessage;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;
import java.util.List;

@Configuration
@EnableSwagger2
public class SwaggerConfiguration {

    @Bean
    public Docket getV0Swagger() {
        return getSwagger("TBD", "v0.1");
    }
    @Bean
    public Docket getV1Swagger() {
        return getSwagger("확정", "v1");
    }
    private Docket getSwagger(String groupName, String version) {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo(version))
                .select()
                .apis(RequestHandlerSelectors.basePackage("star16m"))
                .paths(PathSelectors.ant("/**/" + version + "/**"))
                .build()
                .groupName(groupName)
                .useDefaultResponseMessages(false)
//                .globalResponseMessage(RequestMethod.GET, getResponseMessageList())
                ;
    }
    private ApiInfo apiInfo(String version) {
        return new ApiInfoBuilder()
                .title("spring boot swagger2")
                .description("swagger2 sample")
                .version(version)
                .build();
    }

    private List<ResponseMessage> getResponseMessageList() {
        List<ResponseMessage> responseMessages = new ArrayList<>();
//        responseMessages.add(new ResponseMessageBuilder()
//                .code(500)
//                .message("Internal Server Error")
//                .responseModel(new ModelRef("Error"))
//                .build());
        responseMessages.add(new ResponseMessageBuilder()
                .code(400)
                .message("Bad Request")
                .build());
        responseMessages.add(new ResponseMessageBuilder()
                .code(404)
                .message("Not Found")
                .build());
        return responseMessages;
    }
}
