package star16m.bootsample.resource.service.error;

import lombok.Getter;

public class EntityNotfoundException extends SimpleException {

    @Getter
    private String targetName;

    public EntityNotfoundException(String targetName) {
        super(String.format("Not found target [{}]", targetName));
        this.targetName = targetName;
    }

}
