package star16m.tools.command.script;

import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.MethodParameter;
import org.springframework.shell.CompletionContext;
import org.springframework.shell.CompletionProposal;
import org.springframework.shell.standard.ValueProviderSupport;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
@Setter
public class ScriptValueProvider extends ValueProviderSupport {
    @Autowired
    private ScriptProvider scriptProvider;

    @Override
    public List<CompletionProposal> complete(MethodParameter methodParameter, CompletionContext completionContext, String[] strings) {
        final String word = completionContext.currentWord();
        return this.scriptProvider.commands().stream()
                .filter(s -> s.contains(word))
                .map(CompletionProposal::new)
                .collect(Collectors.toList())
                ;
    }
}
