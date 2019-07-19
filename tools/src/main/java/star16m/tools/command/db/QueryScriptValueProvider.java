package star16m.tools.command.db;

import org.springframework.core.MethodParameter;
import org.springframework.shell.CompletionContext;
import org.springframework.shell.CompletionProposal;
import org.springframework.shell.standard.ValueProviderSupport;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class QueryScriptValueProvider extends ValueProviderSupport {
    private QueryScriptProvider queryScriptProvider;

    public QueryScriptValueProvider(QueryScriptProvider queryScriptProvider) {
        this.queryScriptProvider = queryScriptProvider;
    }

    @Override
    public List<CompletionProposal> complete(MethodParameter methodParameter, CompletionContext completionContext, String[] strings) {
        final String word = completionContext.currentWord();
        return this.queryScriptProvider.scripts().stream()
                .filter(s -> s.contains(word))
                .map(CompletionProposal::new)
                .collect(Collectors.toList())
                ;
    }
}
