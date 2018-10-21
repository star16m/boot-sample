package star16m.bootsample.resource.service.action;

import lombok.Getter;
import lombok.ToString;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Getter
@ToString
public class ActionParameter implements Serializable {
    @NotNull @NotEmpty
    private String parameterName;
    @NotNull @NotEmpty
    private Boolean required;

    public ActionParameter(String parameterName, Boolean required) {
        this.parameterName = parameterName;
        this.required = required;
    }
}
