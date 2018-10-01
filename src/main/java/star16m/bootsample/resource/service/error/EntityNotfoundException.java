package star16m.bootsample.resource.service.error;

public class EntityNotfoundException extends SimpleException {

    public EntityNotfoundException(String message) {
        super(message);
    }

    public EntityNotfoundException(String message, Object... objects) {
        super(message, objects);
    }
}
