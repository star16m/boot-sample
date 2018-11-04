package star16m.bootsample.resource.service.action;

import lombok.Getter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;

@Getter
public class Action implements Serializable {
    @NotNull @NotEmpty @Size(min = 3, max = 20)
    private String actionName;
    @NotNull
    private ActionType readType;
    @NotNull @NotEmpty @Size(min = 3, max = 255)
    private String readDetail;
    @NotNull
    private ActionType writeType;
    @NotNull @NotEmpty @Size(min = 3, max = 255)
    private String writeDetail;
    private String[] columns;
    
    public Action(String actionName, ActionType readType, String readDetail, ActionType writeType, String writeDetail, String...columns) {
        this.actionName = actionName;
        this.readType = readType;
        this.readDetail = readDetail;
        this.writeType = writeType;
        this.writeDetail = writeDetail;
        this.columns = columns;
    }
}
