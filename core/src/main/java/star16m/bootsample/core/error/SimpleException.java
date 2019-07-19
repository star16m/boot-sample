package star16m.bootsample.core.error;

import java.text.MessageFormat;

public class SimpleException extends RuntimeException {
    private String message;

    public SimpleException(String message) {
        this.message = message;
    }

    public SimpleException(String message, Object... objects) {
        this(MessageFormat.format(message, objects));
    }

    @Override
    public String getMessage() {
        return message;
    }
}
