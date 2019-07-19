package star16m.bootsample.core.error;

import lombok.Getter;

public class ResourceNotfoundException extends SimpleException {

    @Getter
    private String targetName;

    public ResourceNotfoundException(String targetName) {
        super(String.format("Not found target [{}]", targetName));
        this.targetName = targetName;
    }

}
