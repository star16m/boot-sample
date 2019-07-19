package star16m.tools.command.db;

import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;
import java.util.Map;

public class RDBDataConnector {
    private static final int QUERY_MAX_ROW = 100;
    private static final String QUERY_SELECT_FORMAT = "SELECT '[' || (row_number() over()) || ']' as rownum, result.* FROM (%s) result limit %d";
    private JdbcTemplate jdbcTemplate;

    public RDBDataConnector(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Map<String, Object>> select(String queryString) {
        return select(queryString, QUERY_MAX_ROW);
    }

    public List<Map<String, Object>> select(String queryString, int maxRow) {
        return this.jdbcTemplate.queryForList(String.format(QUERY_SELECT_FORMAT, queryString, maxRow));
    }
    public List<Map<String, Object>> getTableInfo(String tableName) {
        return select("SELECT col.table_name, col.ordinal_position, column_name, case when col.data_type = 'USER-DEFINED' then col.udt_name when col.data_type = 'ARRAY' then concat(regexp_replace(col.udt_name, '^_', ''), '[]') when col.data_type = 'character varying' then 'text' when col.data_type = 'real' then col.udt_name else col.data_type end as data_type, col.character_maximum_length, col.character_octet_length, col.numeric_precision, col.numeric_scale, col.datetime_precision, col.interval_type, col.is_nullable, col.column_default FROM information_schema.columns col WHERE col.table_name = '" + tableName + "' ORDER BY col.table_name, col.ordinal_position");
    }
}
