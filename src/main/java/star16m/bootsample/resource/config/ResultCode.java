package star16m.bootsample.resource.config;

public enum ResultCode {
    SUCCESS(0, "성공-default"),
    FAIL(9999, "실패-default"),
    TESTS(3333, "테스트 - default")
    ;

    private int code;
    private String description;
    ResultCode(int code, String description) {
        this.code = code;
        this.description = description;
    }
    int getCode() {
        return this.code;
    }
    String getDescription() {
        return this.description;
    }
}
