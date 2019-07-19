package star16m.bootsample.web.config.swagger;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SwaggerConfiguration {

    @Bean
    public Docket getV1Swagger() {
        return getSwagger("v1", "v1");
    }
    @Bean
    public Docket getV2Swagger() {
        return getSwagger("v2", "v2");
    }
    private Docket getSwagger(String groupName, String version) {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo(version))
                .select()
                .apis(RequestHandlerSelectors.basePackage("star16m"))
                .paths(PathSelectors.ant(String.format("/api/rest/%s/**", version)))
                .build()
                .groupName(groupName)
                .useDefaultResponseMessages(false)
                ;
    }
    private ApiInfo apiInfo(String version) {
        return new ApiInfoBuilder()
                .title("spring boot swagger2")
                .description("swagger2 sample")
                .version(version)
                .build();
    }
}
