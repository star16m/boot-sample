package star16m.bootsample.web.model;

public enum ResultCode {
    SUCCESS(0, "성공"),
    FAIL(9999, "실패한 경우"),
    BAD_REQUEST(10001, "잘못된 요청인 경우"),
    BAD_REQUEST_METHOD(10002, "메소드를 잘못 요청한 경우"),
    RESOURCE_NOT_FOUND(8282, "리소스를 찾을 수 없는 경우"),
    INTERNAL_ERROR(10002, "내부 에러가 발생한 경우"),
    AUTHENTICATE_ERROR(20000, "인증 관련 에러"),
    AUTHORITY_ERROR(20001, "권한 관련 에러"),
    INVALID_ARGUMNET(20001, "입력값에 오류가 있는 경우"),
    INVALID_TOKEN(30001, "잘못된 Token"),
    EXPIRED_TOKEN(30002, "만료된 Token")
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
