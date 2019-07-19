package star16m.bootsample.web.service.task;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public abstract class ActionTask extends Thread {

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
