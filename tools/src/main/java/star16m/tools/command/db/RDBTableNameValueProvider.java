package star16m.tools.command.db;

import org.springframework.core.MethodParameter;
import org.springframework.shell.CompletionContext;
import org.springframework.shell.CompletionProposal;
import org.springframework.shell.standard.ValueProviderSupport;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class RDBTableNameValueProvider extends ValueProviderSupport {
    private static final String QUERY_SELECT_TABLE_NAME = "SELECT table_name FROM information_schema.tables WHERE table_schema = 'public' and table_name like '%%%s%%'";
    private RDBDataConnector rdbDataConnector;

    public RDBTableNameValueProvider(RDBDataConnector rdbDataConnector) {
        this.rdbDataConnector = rdbDataConnector;
    }

    @Override
    public List<CompletionProposal> complete(MethodParameter methodParameter, CompletionContext completionContext, String[] strings) {
        String tableName = completionContext.currentWord();
        return this.rdbDataConnector.select(String.format(QUERY_SELECT_TABLE_NAME, tableName)).stream()
                .map(m -> m.get("table_name").toString())
                .map(CompletionProposal::new)
                .collect(Collectors.toList());
    }
}
