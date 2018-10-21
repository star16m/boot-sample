package star16m.bootsample.resource.utils;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class WebUtil {

    public static <T> ResponseEntity<T> response(T object) {
        return response(object, HttpStatus.OK);
    }

    public static <T> ResponseEntity<T> response(T object, HttpStatus httpStatus) {
        return new ResponseEntity<>(object, httpStatus);
    }
}
