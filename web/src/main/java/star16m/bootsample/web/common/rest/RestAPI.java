package star16m.bootsample.web.common.rest;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.lang.Nullable;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;
import star16m.bootsample.core.utils.SimpleUtil;
import star16m.bootsample.web.common.rest.sample.SampleEmployeeInfo;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Map;

public enum RestAPI {
    SAMPLE("http://dummy.restapiexample.com/api/v1/employees", HttpMethod.GET, new ParameterizedTypeReference<List<SampleEmployeeInfo>>() {}),
    SAMPLE2("dummy.restapiexample.com","api/v1/{haha}", HttpMethod.GET, new ParameterizedTypeReference<List<SampleEmployeeInfo>>() {})
    ;

    private String scheme;
    private String host;
    private Integer port;
    private String path;
    private HttpMethod httpMethod;
    private ParameterizedTypeReference<?> typeReference;

    RestAPI(String urlString, HttpMethod httpMethod, ParameterizedTypeReference<?> typeReference) {
        URI uri;
        try {
            uri = new URI(urlString);
        } catch (URISyntaxException e) {
            throw new IllegalArgumentException(e);
        }
        this.scheme = uri.getScheme();
        this.host = uri.getHost();
        this.port = uri.getPort();
        this.path = uri.getPath();
        this.httpMethod = httpMethod;
        this.typeReference = typeReference;
    }
    RestAPI(String host, String path, HttpMethod httpMethod, ParameterizedTypeReference<?> typeReference) {
        this("http", host, 80, path, httpMethod, typeReference);
    }
    RestAPI(String scheme, String host, Integer port, String path, HttpMethod httpMethod, ParameterizedTypeReference<?> typeReference) {
        this.scheme = scheme;
        this.host = host;
        this.port = port;
        this.path = path;
        this.httpMethod = httpMethod;
        this.typeReference = typeReference;
    }

    public URI getURI(@Nullable Map<String, Object> parameterMap, @Nullable Object ... pathVariable) {
        UriComponentsBuilder componentsBuilder = UriComponentsBuilder.newInstance()
                .scheme(this.scheme)
                .host(this.host)
                .port(this.port)
                .path(this.path);
        if (SimpleUtil.isNotNullAndIsNotEmpty(parameterMap)) {
            parameterMap.entrySet().parallelStream().forEach(param -> componentsBuilder.queryParam(param.getKey(), param.getValue()));
        }
        UriComponents uriComponents = componentsBuilder.build();
        if (SimpleUtil.isNotNull(pathVariable) && pathVariable.length > 0) {
            uriComponents = uriComponents.expand(pathVariable);
        }
        return uriComponents.encode().toUri();
    }

    public HttpMethod getHttpMethod() {
        return this.httpMethod;
    }

    public <T> ParameterizedTypeReference<T> getTypeReference() {
        return (ParameterizedTypeReference<T>) this.typeReference;
    }
}
