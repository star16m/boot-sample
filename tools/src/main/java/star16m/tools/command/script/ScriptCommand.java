package star16m.tools.command.script;

import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;
import star16m.bootsample.core.utils.SimpleUtil;
import star16m.tools.command.ToolsUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

@ShellComponent
public class ScriptCommand {

    private ScriptProvider scriptProvider;

    public ScriptCommand(ScriptProvider scriptProvider) {
        this.scriptProvider = scriptProvider;
    }
    @ShellMethod(value = "application.yml 에 정의된 script command 를 실행합니다.")
    public Object scripts(@ShellOption(valueProvider = ScriptValueProvider.class, value = "-script", help = "실행할 script command key 를 정의합니다.") String scriptCommandKey) throws IOException {

        if (SimpleUtil.isNull(scriptCommandKey) || !this.scriptProvider.hasScriptCommand(scriptCommandKey)) {
            return ToolsUtils.getTableModel("NO_SCRIPT_COMMAND");
        }
        Runtime runtime = Runtime.getRuntime();
        String scriptCommand = this.scriptProvider.getScriptCommand(scriptCommandKey);
        Process process = runtime.exec(new String[]{"/bin/sh", "-c", scriptCommand});
        try (BufferedReader br = new BufferedReader(new InputStreamReader(process.getInputStream()))) {
            String line;
            List<String> resultList = new ArrayList<>();
            while ((line = br.readLine()) != null) {
                resultList.add(line);
            }
            return resultList;
        }
    }
}
