package star16m.bootsample.web.service.task;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Slf4j
@Component("PRINT")
@Scope("prototype")
public class PrintInfoTask extends ActionTask {
    public PrintInfoTask() {
        super("PrintInfo");
    }

    public void execute() {
        log.info("[{}->{}] this is printInfoTask", this.getId(), this.getName());
    }
}
