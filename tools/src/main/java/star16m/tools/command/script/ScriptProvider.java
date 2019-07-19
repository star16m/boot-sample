package star16m.tools.command.script;

import lombok.Setter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
@Setter
@ConditionalOnProperty(prefix = "app.command.script", value = "enabled", havingValue = "true", matchIfMissing = true)
@ConfigurationProperties(prefix = "app.command.script")
public class ScriptProvider {

    private Map<String, String> commands;

    public String getScriptCommand(String scriptCommandKey) {
        return this.commands.get(scriptCommandKey);
    }

    public boolean hasScriptCommand(String scriptCommandKey) {
        return this.commands.containsKey(scriptCommandKey);
    }

    public List<String> commands() {
        return this.commands.keySet().stream().collect(Collectors.toList());
    }
}
