package star16m.bootsample.web.service.action;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.Collections;
import java.util.Map;

@NoArgsConstructor
@Data
public class ActionRequest implements Serializable {
    @NotNull
    @NotEmpty
    @Size(min = 3, max = 20)
    private String actionName;
    private Map<String, Object> actionParameter = Collections.emptyMap();
}
