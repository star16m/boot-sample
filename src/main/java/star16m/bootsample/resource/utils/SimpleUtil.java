package star16m.bootsample.resource.utils;

import star16m.bootsample.resource.service.error.SimpleException;

import java.util.Collection;
import java.util.Objects;

public class SimpleUtil {
    private SimpleUtil() {}
    public static boolean isNull(Object object) {
        return Objects.isNull(object);
    }
    public static boolean isNotNull(Object object) {
        return !isNull(object);
    }
    public static void mustMin(Collection collection, int minSize) {
        if (collection == null || collection.size() < minSize) {
            throw new SimpleException("Object cannot be longer than {} characters", minSize);
        }
    }
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
        mustNotNull(a);
        mustNotNull(b);
        if (!a.equals(b)) {
            throw new SimpleException("Objects must be equals. a=[{}], b=[{}]", a, b);
        }
    }
}
