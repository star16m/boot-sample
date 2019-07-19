package star16m.tools.command.db;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;
import star16m.tools.command.ToolsUtils;

@ConditionalOnProperty(prefix = "app.command.rdb", value = "enabled", havingValue = "true", matchIfMissing = true)
@ShellComponent
public class RDBSelectCommand {

    private RDBDataConnector rdbDataConnector;
    public RDBSelectCommand(RDBDataConnector rdbDataConnector) {
        this.rdbDataConnector = rdbDataConnector;
    }

    @ShellMethod(key = "select", value = "RDB 를 직접 조회하여 출력합니다.")
    public Object select(@ShellOption(value = "-query", help = "DB Query 를 직접 입력합니다.") String query,
                         @ShellOption(value = "-h", help = "header 를 출력합니다.") boolean addHeader) {
        return ToolsUtils.getTableModel(this.rdbDataConnector.select(query), addHeader);
    }

}
