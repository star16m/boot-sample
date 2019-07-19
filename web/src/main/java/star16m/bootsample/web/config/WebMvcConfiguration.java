package star16m.bootsample.web.config;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.conn.ssl.TrustSelfSignedStrategy;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.ssl.SSLContexts;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.web.servlet.WebMvcRegistrations;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.style.ToStringCreator;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;
import star16m.bootsample.core.error.ResourceNotfoundException;
import star16m.bootsample.core.error.SimpleException;
import star16m.bootsample.core.error.SimpleRequestException;
import star16m.bootsample.core.utils.SimpleUtil;
import star16m.bootsample.web.common.SimpleResponse;
import star16m.bootsample.web.model.ResultCode;

import javax.net.ssl.SSLContext;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RestControllerAdvice
@Configuration
public class WebMvcConfiguration implements WebMvcConfigurer, WebMvcRegistrations {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/api/rest/**")
                .allowedOrigins("*")
                .allowedMethods(Arrays.asList(HttpMethod.values()).stream()
                        .map(HttpMethod::name)
                        .collect(Collectors.toList())
                        .toArray(new String[0]))
                .allowCredentials(false)
                .maxAge(3600);
    }

    @Override
    public RequestMappingHandlerMapping getRequestMappingHandlerMapping() {
        return new CommonRequestMappingHandlerMapping();
    }
    @Bean
    public Jackson2ObjectMapperBuilder getObjectMapperBuilder() {
        Jackson2ObjectMapperBuilder builder = new Jackson2ObjectMapperBuilder();
        builder.serializationInclusion(JsonInclude.Include.NON_NULL);
        return builder;
    }

    @Bean
    public ObjectMapper objectMapper() {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
        objectMapper.setDefaultPropertyInclusion(JsonInclude.Value.construct(
                JsonInclude.Include.ALWAYS,
                JsonInclude.Include.NON_NULL));
        return objectMapper;
    }

    @Bean
    public RestTemplate customRestTemplate(@Autowired ObjectMapper objectMapper) throws KeyStoreException, NoSuchAlgorithmException, KeyManagementException {
        SSLContext sslContext = SSLContexts.custom().loadTrustMaterial(new TrustSelfSignedStrategy()).build();
        CloseableHttpClient httpClient = HttpClients.custom()
                .setSSLContext(sslContext)
                .setSSLHostnameVerifier(NoopHostnameVerifier.INSTANCE)
                .build();

        HttpComponentsClientHttpRequestFactory requestFactory = new HttpComponentsClientHttpRequestFactory();
        RestTemplate restTemplate = new RestTemplate(requestFactory);
        ((HttpComponentsClientHttpRequestFactory) restTemplate.getRequestFactory()).setHttpClient(httpClient);

        MappingJackson2HttpMessageConverter jsonMessageConverter = new MappingJackson2HttpMessageConverter();
        jsonMessageConverter.setSupportedMediaTypes(Arrays.asList(
                MediaType.APPLICATION_JSON,
                MediaType.APPLICATION_OCTET_STREAM
        ));
        jsonMessageConverter.setObjectMapper(objectMapper);

        List<HttpMessageConverter<?>> messageConverters = new ArrayList<>();
        messageConverters.add(jsonMessageConverter);
        restTemplate.getMessageConverters().addAll(messageConverters);
        restTemplate.getInterceptors().add((request, body, execution) -> {
            ClientHttpResponse response = execution.execute(request,body);
            response.getHeaders().setContentType(MediaType.APPLICATION_JSON);
            return response;
        });

        return restTemplate;
    }

    @ExceptionHandler(ResourceNotfoundException.class)
    public final SimpleResponse<?> handleResourceNotfoundException(ResourceNotfoundException e, WebRequest request) {
        return handle(e, request, ResultCode.RESOURCE_NOT_FOUND);
    }
    @ExceptionHandler(SimpleRequestException.class)
    public final SimpleResponse<?> handleSimpleRequestException(SimpleRequestException e, WebRequest request) {
        return handle(e, request, ResultCode.BAD_REQUEST);
    }
    @ExceptionHandler(SimpleException.class)
    public final SimpleResponse<?> handlerSimpleException(SimpleException e, WebRequest request) {
        return handle(e, request, ResultCode.INTERNAL_ERROR);
    }
    @ExceptionHandler(Exception.class)
    public final SimpleResponse<?> handleException(Throwable e, WebRequest request) {
        return handle(e, request, ResultCode.FAIL);
    }

    private final SimpleResponse<?> handle(Throwable e, WebRequest request, ResultCode resultCode) {
        if (log.isErrorEnabled()) {
            log.error("Error occurred resultCode [{}]", resultCode);
        }
        if (log.isTraceEnabled()) {
            log.trace("Error request context[{}], remoteUser[{}], principalUser[{}], requestParameter[{}], exceptionMessage[{}]",
                    request.getContextPath(),
                    request.getRemoteUser(),
                    request.getUserPrincipal(),
                    SimpleUtil.getString(request.getParameterMap()),
                    e.getMessage(),
                    e
            );
        }
        return SimpleResponse.emptyResponse(resultCode);
    }
}