package star16m.tools.command;

import org.springframework.core.MethodParameter;
import org.springframework.shell.CompletionContext;
import org.springframework.shell.CompletionProposal;
import org.springframework.shell.standard.ValueProviderSupport;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class PrintTypeProvider extends ValueProviderSupport {

    @Override
    public List<CompletionProposal> complete(MethodParameter methodParameter, CompletionContext completionContext, String[] hintWords) {
        return Arrays.asList(PrintType.values()).stream().map(PrintType::name).map(CompletionProposal::new).collect(Collectors.toList());
    }
}
