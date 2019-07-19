package star16m.bootsample.web.common;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import star16m.bootsample.web.model.ResultCode;

import java.io.Serializable;

@Data
@AllArgsConstructor
public class SimpleResponse<T> implements Serializable {
    private static final long serialVersionUID = -216154267378726512L;
    private T body;
    private ResultCode result;
    public int getResultCode() {
        return this.result.getCode();
    }
    @JsonProperty("isOk")
    public boolean isOk() {
        return this.result.equals(ResultCode.SUCCESS);
    }
    public static <T> SimpleResponse<T> emptyResponse() {
        return new EmptyBody<>(ResultCode.SUCCESS);
    }
    public static <T> SimpleResponse<T> emptyResponse(ResultCode resultCode) {
        return new EmptyBody<>(resultCode);
    }
    public static <T> SimpleResponse<T> of(T body) {
        return of(body, ResultCode.SUCCESS);
    }
    public static <T> SimpleResponse<T> of(T body, ResultCode resultCode) {
        return new SimpleResponse<>(body, resultCode);
    }
    private static class EmptyBody<T> extends SimpleResponse<T> {
        private ResultCode resultCode;
        EmptyBody(ResultCode resultCode) {
            super(null, resultCode);
        }
        public T getBody() {
            return null;
        }
    }
}
