package star16m.bootsample.web.service.action;

import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Component;
import star16m.bootsample.web.service.action.Action;

import java.util.List;
import java.util.Map;

@Component
public class ActionExecutor {
    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;
    private final String NAMED_QUERY_SELECT = "select result.* from (%s) as result";

    public ActionExecutor(NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    }

    public List<Map<String, Object>> getList(Action action, Map<String, Object> parameters) {
        return this.namedParameterJdbcTemplate.queryForList(String.format(NAMED_QUERY_SELECT, action.getReadDetail()), parameters);
    }

}
