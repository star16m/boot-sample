package star16m.tools.command;

import org.springframework.core.env.AbstractEnvironment;
import org.springframework.core.env.Environment;
import org.springframework.core.env.MapPropertySource;
import org.springframework.core.env.PropertySource;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Collectors;

import static java.util.Map.Entry.comparingByKey;

@ShellComponent
public class ConfigCommand {
    private Map<String, Object> appKeyMapList;

    public ConfigCommand(Environment environment) {
        Map<String, Object> map = new HashMap<>();
        for (Iterator<PropertySource<?>> it = ((AbstractEnvironment) environment).getPropertySources().iterator(); ((Iterator) it).hasNext(); ) {
            PropertySource<?> propertySource = (PropertySource<?>) it.next();
            if (propertySource instanceof MapPropertySource) {
                map.putAll(((MapPropertySource) propertySource).getSource());
            }
        }
        this.appKeyMapList = map.entrySet().stream().filter(e -> e.getKey().startsWith("app")).sorted(comparingByKey()).collect(
                Collectors.toMap(
                        Map.Entry::getKey,
                        e -> environment.getProperty(e.getKey()),
                        (v1, v2) -> {
                            throw new RuntimeException(String.format("Duplicate application key. key[%s], value[%s]", v1, v2));
                        },
                        TreeMap::new));
    }

    @ShellMethod(key = {"config"}, value = "설정값을 출력합니다")
    public Object config() {
        return ToolsUtils.getTableModel(this.appKeyMapList, false);
    }
}
