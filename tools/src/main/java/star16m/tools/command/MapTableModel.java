package star16m.tools.command;

import org.springframework.shell.table.TableModel;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class MapTableModel extends TableModel {
    private Map<String, Object> data;
    private List<String> keys;
    private List<Object> values;

    public MapTableModel(Map<String, Object> data) {
        this.data = data;
        this.keys = data.keySet().stream().collect(Collectors.toList());
        this.values = data.values().stream().collect(Collectors.toList());
    }

    @Override
    public int getColumnCount() {
        return 2;
    }
    @Override
    public int getRowCount() {
        return keys.size();
    }

    @Override
    public Object getValue(int row, int col) {
        if (col == 0) {
            return keys.get(row);
        } else {
            return values.get(row);
        }
    }
}
