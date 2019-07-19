package star16m.tools.command;

import java.util.List;
import java.util.Map;
import java.util.function.BiFunction;

public enum PrintType {
    map((r,h) -> r),
    json((r,h) -> ToolsUtils.getJsonArray(r)),
    table(ToolsUtils::getTableModel),
    ;

    private final BiFunction<List<Map<String, Object>>, Boolean, Object> wrapper;

    PrintType(BiFunction<List<Map<String, Object>>, Boolean, Object> wrapper) {
        this.wrapper = wrapper;
    }

    public Object wrap(List<Map<String, Object>> result, Boolean addHeader) {
        return this.wrapper.apply(result, addHeader);
    }

}
