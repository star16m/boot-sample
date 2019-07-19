package star16m.tools.command.db;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;
import org.springframework.shell.table.Table;
import star16m.tools.command.ToolsUtils;

@ConditionalOnProperty(prefix = "app.command.rdb", value = "enabled", havingValue = "true", matchIfMissing = true)
@ShellComponent
public class RDBTableCommand {

    private RDBDataConnector rdbDataConnector;
    public RDBTableCommand(RDBDataConnector rdbDataConnector) {
        this.rdbDataConnector = rdbDataConnector;
    }

    @ShellMethod(key = "table", value = "RDB Table 의 정의를 출력합니다.")
    public Table table(@ShellOption(valueProvider = RDBTableNameValueProvider.class, help = "Table 명", value = "-tablename") String tableName,
                       @ShellOption(value = "-h", help = "header 를 출력합니다.") boolean addHeader) {
        return ToolsUtils.getTableModel(this.rdbDataConnector.getTableInfo(tableName), addHeader);
    }

}
