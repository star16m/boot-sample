package star16m.bootsample.core.utils;

import star16m.bootsample.core.error.SimpleException;

import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class SimpleUtil {
    private SimpleUtil() {}
    public static boolean isNull(Object object) {
        return Objects.isNull(object);
    }
    public static boolean isNotNull(Object object) {
        return !isNull(object);
    }
    public static boolean isEmpty(String string) {
        return isNull(string) || string.isEmpty();
    }
    public static boolean isEmpty(Collection<?> collection) {
        return isNull(collection) || collection.isEmpty();
    }
    public static boolean isEmpty(Map<?,?> map) {
        return isNull(map) || map.isEmpty();
    }
    public static boolean isEmpty(Object[] objectArray) {
        return isNull(objectArray) || objectArray.length == 0;
    }
    public static <T> void must(T t, Predicate<T> predicate, String message) {
        Objects.requireNonNull(predicate);
        if (predicate.test(t)) {
            throw new SimpleException(message);
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
    public static String getString(String message) {
        return getString(message, "");
    }
    public static String getString(String message, String defaultMessage) {
        if (isNull(message) || message.isEmpty()) {
            return defaultMessage;
        }
        return message;
    }
    public static String getString(Map<String, String[]> map) {
        if (isNull(map) || map.isEmpty()) {
            return "";
        }
        return map.entrySet().stream()
                .map(e -> e.getKey() + " => " + getString(e.getValue()))
                .collect(Collectors.joining(", ", "{", "}"));
    }

    public static String getString(String[] stringArray) {
        if (isNull(stringArray) || isEmpty(stringArray)) {
            return "";
        }
        return Arrays.asList(stringArray).stream().collect(Collectors.joining(", "));
    }
    public static boolean isNotNullAndIsNotEmpty(Map<String, Object> map) {
        return isNotNull(map) && !map.isEmpty();
    }
}
