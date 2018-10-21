package star16m.bootsample.resource.service.task;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component("PRINT")
@Scope("prototype")
public class PrintInfoTask extends ActionTask {
    public PrintInfoTask() {
        super("PrintInfo");
    }

    public void execute() {
        log.info("[{}-{}] this is printInfoTask", this.getId(), this.getName());
    }
}
