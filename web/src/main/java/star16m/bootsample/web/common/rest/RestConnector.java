package star16m.bootsample.web.common.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.util.Arrays;
import java.util.Map;

@Component
public class RestConnector {
    private static final HttpHeaders DEFAULT_REST_HEADERS = new HttpHeaders();
    static {
        DEFAULT_REST_HEADERS.setAccept(Arrays.asList(new MediaType[] {
                MediaType.APPLICATION_JSON_UTF8,
                MediaType.APPLICATION_OCTET_STREAM
        }));
    }
    @Autowired
    private RestTemplate restTemplate;

    public <I, O> O connect(@NonNull RestAPI restAPI) {
        return this.<I, O>connect(restAPI, null, null);
    }
    public <I, O> O connect(@NonNull RestAPI restAPI, @Nullable Map<String, Object> parameterMap, @Nullable HttpHeaders headerInfo, Object ... pathVariable) {
        if (headerInfo == null) {
            headerInfo = new HttpHeaders();
        }
        headerInfo.addAll(DEFAULT_REST_HEADERS);
        HttpEntity<I> httpEntity = new HttpEntity<>(headerInfo);
        URI connectURI = restAPI.getURI(parameterMap, pathVariable);

        ResponseEntity<O> responseEntity = this.restTemplate.exchange(
                connectURI,
                restAPI.getHttpMethod(),
                httpEntity,
                restAPI.getTypeReference()
        );
        return responseEntity.getBody();
    }

}
