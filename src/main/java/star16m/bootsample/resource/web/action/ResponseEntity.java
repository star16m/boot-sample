package star16m.bootsample.resource.web.action;

import lombok.Data;
import org.springframework.http.HttpStatus;

import java.io.Serializable;

@Data
public class ResponseEntity<T> implements Serializable {
    private T data;
    private HttpStatus httpStatus;

    public ResponseEntity(T data) {
        this(data, HttpStatus.OK);
    }
    public ResponseEntity(T data, HttpStatus httpStatus) {
        this.data = data;
        this.httpStatus = httpStatus;
    }

    public boolean isSuccess() {
        return this.httpStatus.equals(HttpStatus.OK);
    }
}
