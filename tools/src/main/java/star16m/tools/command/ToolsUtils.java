package star16m.tools.command;

import lombok.extern.slf4j.Slf4j;
import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
import org.springframework.shell.table.*;
import star16m.bootsample.core.utils.SimpleUtil;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Slf4j
public class ToolsUtils {
    private static final String NULL_STRING = "<NULL>";
    private static final Table NO_DATA = getTableModel("<NO_DATA>");
    private ToolsUtils() {}

    public static JSONArray getJsonArray(List<Map<String, Object>> list) {
        JSONArray jsonArray = new JSONArray();
        try {
            for (Map<String, Object> map : list) {
                jsonArray.put(getJsonStringFromMap(map));
            }
        } catch (JSONException e) {
            log.error("error occurred while parse json from result map.", e);
            throw new RuntimeException(e);
        }
        return jsonArray;
    }

    private static JSONObject getJsonStringFromMap(Map<String, Object> map) throws JSONException {
        JSONObject jsonObject = new JSONObject();
        for (Map.Entry<String, Object> entry : map.entrySet()) {
            jsonObject.put(entry.getKey(), entry.getValue());
        }

        return jsonObject;
    }

    public static Table getTableModel() {
        return NO_DATA;
    }
    public static Table getTableModel(String result) {
        Map<String, Object> data = new HashMap<>();
        data.put("result", result);
        return getTableModel(data, false);
    }
    public static Table getTableModel(String[][] data, boolean addHeader) {
        if (SimpleUtil.isEmpty(data)) {
            return NO_DATA;
        }
        TableModel tableModel = new ArrayTableModel(data);
        return getTableModel(tableModel, addHeader);
    }
    public static Table getTableModel(List<Map<String, Object>> dataList, boolean addHeader) {
        if (SimpleUtil.isEmpty(dataList)) {
            return NO_DATA;
        }

        if (addHeader) {
            Map<String, Object> headerMap = new LinkedHashMap<>();
            dataList.get(0).entrySet().stream().forEach(e -> headerMap.put(e.getKey(), e.getKey()));
            dataList.add(0, headerMap);
        }
        return getTableModel(dataList.stream().map(m -> m.values().stream()
                .map(o -> SimpleUtil.isNull(o) ? NULL_STRING : o.toString())
                .toArray(String[]::new)).toArray(String[][]::new), addHeader);
    }
    public static Table getTableModel(Map<String, Object> data, Boolean addHeader) {
        if (SimpleUtil.isEmpty(data)) {
            return NO_DATA;
        }
        TableModel tableModel = new MapTableModel(data);
        return getTableModel(tableModel, addHeader);
    }
    public static Table getTableModel(TableModel tableModel, Boolean addHeader) {
        TableBuilder tableBuilder = new TableBuilder(tableModel);
        tableBuilder.on(allCell()).addSizer(koreanSizeConstrains());
        tableBuilder.on(allCell()).addWrapper(getTextWrapper());
        tableBuilder.on(allCell()).addAligner(koreanSizeAligner());
        tableBuilder.addInnerBorder(BorderStyle.fancy_light).addOutlineBorder(BorderStyle.fancy_double);
        if (addHeader) {
            tableBuilder.on(CellMatchers.row(0)).addAligner(SimpleHorizontalAligner.center);
        }
        return tableBuilder.build();
    }
    private static CellMatcher allCell() {
        return (i, j, tableModel) -> true;
    }

    private static SizeConstraints koreanSizeConstrains() {
        return (lines, tableWidth, nbColumns) -> {
            int max = 0;
            int min = 0;
            for (String line : lines) {
                String[] words = line.split("-");
                for (int j = 0; j < words.length; j++) {
                    String word = words[j];
                    min = Math.max(min, getRealStringLength(word));
                }
                max = Math.max(max, getRealStringLength(line));
            }
            return new SizeConstraints.Extent(min, max);
        };
    }
    private static Aligner koreanSizeAligner() {
        return (String[] text, int cellWidth, int cellHeight) -> {
            String[] result = new String[cellHeight];
            for (int i = 0; i < cellHeight; ++i) {
                String line = i < text.length && SimpleUtil.isNotNull(text[i]) ? text[i] : "";
                int len = line.length();
                int realLength = getRealStringLength(line);
                if (len != realLength) {
                    line = line.substring(0, line.lastIndexOf(IntStream.range(0, realLength - len).mapToObj(s -> " ").collect(Collectors.joining(""))));
                }
                result[i] = line;
            }
            return result;
        };
    }
    public static int getRealStringLength(String string) {
        int len = string.length();
        for (int i = 0; i < string.length(); i++) {
            char ch = string.charAt(i);
            if (!isHalfWidth(ch)) {
                len++;
            }
        }
        return len;
    }
    private static boolean isHalfWidth(char ch) {
        // 특수문자
        if (ch < 0x0020) {
            return true;
        }
        // ASCII(Latin characters, symbols, func, numbers)
        if (0x0020 <= ch && ch <= 0x007F) {
            return true;
        }
        // FF61 ~ FF64 : HalfWidth CJK
        // FF65 ~ FF9F : HalfWidth Katakana
        // FFA0 ~ FFDC : HalfWidth Hangul
        if (0xFF61 <= ch && ch <= 0xFFDC) {
            return true;
        }
        // FFE8 ~ FFEE : HalfWidth symbol
        if (0xFFE8 <= ch && ch <= 0xFFEE) {
            return true;
        }
        return false;
    }
    private static TextWrapper getTextWrapper() {
        return (original, columnWidth) -> {
            List<String> result = new ArrayList<>(original.length);
            int originalLength = original.length;

            for (int columnIndex = 0; columnIndex < originalLength; columnIndex++) {
                String line;
                int split;
                for (line = original[columnIndex]; line.length() > columnWidth; line = line.substring(split == -1 ? columnWidth : split + 1)) {
                    split = line.lastIndexOf(' ', columnWidth);
                    String toAdd = split == -1 ? line.substring(0, columnWidth) : line.substring(0, split);
                    result.add(String.format("%-" + columnWidth + "s", toAdd));
                }
                if (columnWidth > 0) {
                    int spaceWidth = columnWidth - line.length() - (getRealStringLength(line) - line.length());
                    result.add(line + IntStream.range(0, spaceWidth).mapToObj(i -> " ").collect(Collectors.joining("")));
                }
            }
            return (String[]) result.toArray(new String[result.size()]);
        };
    }
}
