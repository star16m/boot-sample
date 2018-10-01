package star16m.bootsample.resource.utils;

import org.springframework.http.HttpStatus;
import star16m.bootsample.resource.service.error.SimpleException;
import star16m.bootsample.resource.web.action.ResponseEntity;

import java.util.Objects;

public class SimpleUtil {
    private SimpleUtil() {}

    public static void mustNotNull(Object object) {
        if (isNull(object)) {
            throw new SimpleException("Object must not be null");
        }
    }

    public static void mustNull(Object object) {
        if (!isNull(object)) {
            throw new SimpleException("Object must be null");
        }
    }

    public static void mustEqual(Object a, Object b) {
        Objects.requireNonNull(a);
        Objects.requireNonNull(b);
        if (!a.equals(b)) {
            throw new SimpleException("Objects must be equals. a=[{}], b=[{}]", a, b);
        }
    }

    public static <T> ResponseEntity<T> response(T object) {
        return response(object, HttpStatus.OK);
    }

    public static <T> ResponseEntity<T> response(T object, HttpStatus httpStatus) {
        return new ResponseEntity<>(object, httpStatus);
    }

    public static boolean isNull(Object object) {
        return Objects.isNull(object);
    }
    public static boolean isNotNull(Object object) {
        return !isNull(object);
    }
}
