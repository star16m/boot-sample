package star16m.bootsample.resource.service.task;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class ActionTask extends Thread {

    protected static final Logger log = LoggerFactory.getLogger(ActionTask.class);
    private final String actionName;
    protected ActionTask(String actionName) {
        this.actionName = actionName;
    }
    @Override
    public final void run() {
        log.debug("start action {}", this.actionName);
        execute();
        log.debug("end action {}", this.actionName);
    }

    protected abstract void execute();
}
