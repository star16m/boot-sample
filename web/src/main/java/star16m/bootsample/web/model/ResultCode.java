package star16m.bootsample.web.model;

public enum ResultCode {
    SUCCESS(0, "성공"),
    FAIL(9999, "실패한 경우"),
    BAD_REQUEST(10001, "잘못된 요청인 경우"),
    RESOURCE_NOT_FOUND(8282, "리소스를 찾을 수 없는 경우"),
    INTERNAL_ERROR(10002, "내부 에러가 발생한 경우"),
    ;

    private int code;
    private String description;
    ResultCode(int code, String description) {
        this.code = code;
        this.description = description;
    }
    public int getCode() {
        return this.code;
    }
    public String getDescription() {
        return this.description;
    }
}
