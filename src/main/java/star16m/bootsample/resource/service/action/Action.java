package star16m.bootsample.resource.service.action;

import lombok.Getter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Getter
public class Action implements Serializable {
    @NotNull @NotEmpty @Size(min = 3, max = 20)
    private String actionName;
    @NotNull @NotEmpty @Size(min = 3, max = 255)
    private String actionDetail;
    private List<ActionParameter> actionParameterList;

    public Action(String actionName, String actionDetail, ActionParameter... actionParameterArray) {
        this.actionName = actionName;
        this.actionDetail = actionDetail;
        this.actionParameterList = Arrays.asList(actionParameterArray);
    }

    public List<ActionParameter> getRequiredActionParameter() {
        return this.actionParameterList.stream().filter(p -> p.getRequired()).collect(Collectors.toList());
    }
}
