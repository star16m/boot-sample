package star16m.bootsample.web.config.swagger;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.AuthorizationScopeBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.ApiKey;
import springfox.documentation.service.AuthorizationScope;
import springfox.documentation.service.SecurityReference;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.Arrays;
import java.util.List;

@Configuration
@EnableSwagger2
public class SwaggerConfiguration {
    @Value("${app.security.header.key:Authorization}")
    private String headerKey;
    @Value("${app.swagger.security.header.key:fake-token-for-swagger}")
    private String swaggerHeaderKey;
    @Bean
    public Docket getV1Swagger() {
        return getSwagger("v1", "v1");
    }
    @Bean
    public Docket getV2Swagger() {
        return getSwagger("v2", "v2");
    }
    private Docket getSwagger(String groupName, String version) {
        AuthorizationScope authScopes = new AuthorizationScopeBuilder().scope("global").description("full access").build();
        SecurityReference securityReference = SecurityReference.builder().reference(headerKey).scopes(new AuthorizationScope[]{authScopes}).build();
        SecurityReference securityReference4Swagger = SecurityReference.builder().reference(swaggerHeaderKey).scopes(new AuthorizationScope[]{authScopes}).build();
        List<SecurityContext> securityContexts = Arrays.asList(SecurityContext.builder().securityReferences(Arrays.asList(securityReference, securityReference4Swagger)).build());
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo(version))
                .select()
                .apis(RequestHandlerSelectors.basePackage("star16m"))
                .paths(PathSelectors.ant(String.format("/api/rest/%s/**", version)))
                .build()
                .groupName(groupName)
                .useDefaultResponseMessages(false)
                .securitySchemes(Arrays.asList(
                        new ApiKey(headerKey, headerKey, "header"),
                        new ApiKey(swaggerHeaderKey, swaggerHeaderKey, "header"))
                )
                .securityContexts(securityContexts)
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
