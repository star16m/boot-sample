package star16m.bootsample.core.utils;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class WebUtil {

    private WebUtil() {}

    public static <T> ResponseEntity<T> response(T object) {
        return response(object, HttpStatus.OK);
    }

    public static <T> ResponseEntity<T> response(T object, HttpStatus httpStatus) {
        return new ResponseEntity<>(object, httpStatus);
    }
}
