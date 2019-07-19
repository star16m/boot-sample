package star16m.bootsample.core.utils;

import star16m.bootsample.core.error.SimpleException;

import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public interface SimpleUtil {
    static boolean isNull(Object object) {
        return Objects.isNull(object);
    }
    static boolean isNotNull(Object object) {
        return !isNull(object);
    }
    static boolean isEmpty(String string) {
        return isNull(string) || string.isEmpty();
    }
    static boolean isEmpty(Collection<?> collection) {
        return isNull(collection) || collection.isEmpty();
    }
    static boolean isEmpty(Map<?,?> map) {
        return isNull(map) || map.isEmpty();
    }
    static boolean isEmpty(Object[] objectArray) {
        return isNull(objectArray) || objectArray.length == 0;
    }
    static <T> void must(T t, Predicate<T> predicate, String message) {
        Objects.requireNonNull(predicate);
        if (predicate.test(t)) {
            throw new SimpleException(message);
        }
    }
    static void mustNotNull(Object object) {
        if (isNull(object)) {
            throw new SimpleException("Object must not be null");
        }
    }

    static void mustNull(Object object) {
        if (!isNull(object)) {
            throw new SimpleException("Object must be null");
        }
    }

    static void mustEqual(Object a, Object b) {
        mustNotNull(a);
        mustNotNull(b);
        if (!a.equals(b)) {
            throw new SimpleException("Objects must be equals. a=[{}], b=[{}]", a, b);
        }
    }
    static String getString(String message) {
        return getString(message, "");
    }
    static String getString(String message, String defaultMessage) {
        if (isNull(message) || message.isEmpty()) {
            return defaultMessage;
        }
        return message;
    }
    static String getString(Map<String, String[]> map) {
        if (isNull(map) || map.isEmpty()) {
            return "";
        }
        return map.entrySet().stream()
                .map(e -> e.getKey() + " => " + getString(e.getValue()))
                .collect(Collectors.joining(", ", "{", "}"));
    }

    static String getString(String[] stringArray) {
        if (isNull(stringArray) || isEmpty(stringArray)) {
            return "";
        }
        return Arrays.asList(stringArray).stream().collect(Collectors.joining(", "));
    }
    static boolean isNotNullAndIsNotEmpty(Map<String, Object> map) {
        return isNotNull(map) && !map.isEmpty();
    }
}
