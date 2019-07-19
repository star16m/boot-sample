package star16m.tools.command.db;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@ConditionalOnProperty(prefix = "app.command.rdb", value = "enabled", havingValue = "true", matchIfMissing = true)
@Component
@ConfigurationProperties(prefix = "app.command.rdb.query")
@Getter
@Setter
public class QueryScriptProvider {
    private Map<String, String> scripts;
    public boolean hasScript(String scriptKey) {
        return this.scripts().contains(scriptKey);
    }

    public List<String> scripts() {
        return this.scripts.keySet().stream().collect(Collectors.toList());
    }
    public String getQuery(String scriptKey) {
        if (this.scripts.containsKey(scriptKey)) {
            return this.scripts.get(scriptKey);
        }
        return null;
    }
}
