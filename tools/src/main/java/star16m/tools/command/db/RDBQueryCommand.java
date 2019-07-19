package star16m.tools.command.db;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;
import star16m.bootsample.core.utils.SimpleUtil;
import star16m.tools.command.ToolsUtils;

@ConditionalOnProperty(prefix = "app.command.rdb", value = "enabled", havingValue = "true", matchIfMissing = true)
@ShellComponent
public class RDBQueryCommand {

    private RDBDataConnector rdbDataConnector;
    private QueryScriptProvider queryScriptProvider;
    public RDBQueryCommand(RDBDataConnector rdbDataConnector, QueryScriptProvider queryScriptProvider) {
        this.rdbDataConnector = rdbDataConnector;
        this.queryScriptProvider = queryScriptProvider;
    }

    @ShellMethod(key = "query", value = "application.yml 파일에 미리 정의된 Query 를 실행합니다.")
    public Object query(@ShellOption(value = "-script", help = "application.yml 파일에 미리 정의된 Query 를 선택합니다.", valueProvider = QueryScriptValueProvider.class) String script,
                        @ShellOption(value = "-h", help = "header 를 출력합니다.") boolean addHeader) {
        if (SimpleUtil.isNotNull(script) && this.queryScriptProvider.hasScript(script)) {
            return ToolsUtils.getTableModel(this.rdbDataConnector.select(this.queryScriptProvider.getQuery(script)), addHeader);
        }
        return ToolsUtils.getTableModel("NO_QUERY");
    }
}
