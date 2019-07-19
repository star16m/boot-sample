package star16m.bootsample.core.error;

public class SimpleRequestException extends SimpleException {
    private final String requestName;
    public SimpleRequestException(String requestName) {
        super("bad reqeust");
        this.requestName = requestName;
    }

    public String getRequestName() {
        return this.requestName;
    }
}