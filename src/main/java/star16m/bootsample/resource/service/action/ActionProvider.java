package star16m.bootsample.resource.service.action;

import org.springframework.stereotype.Component;
import star16m.bootsample.resource.utils.SimpleUtil;

import java.util.*;

@Component
public class ActionProvider {
    private final Map<String, Action> actionMap;

    public ActionProvider() {
        this.actionMap = new HashMap<>();
        ActionParameter actionParameter1 = new ActionParameter("i_param1", true);
        ActionParameter actionParameter2 = new ActionParameter("i_param2", false);

        addAction(new Action("action-1", "sp_action_1()"));
        addAction(new Action("action-2", "sp_action_2(i_param1 := :i_param1)", actionParameter1));
        addAction(new Action("action-3", "sp_action_3(i_param1 := :i_param1, i_param2 := :i_param2)", actionParameter1, actionParameter2));
    }

    public void addAction(final Action action) {
        this.actionMap.put(action.getActionName(), action);
    }

    public Optional<Action> getAction(final String actionName) {
        Action action = this.actionMap.get(actionName);
        return Optional.ofNullable(action);
    }

    public List<Action> getAllAction() {
        return new ArrayList<>(this.actionMap.values());
    }

    public boolean isValidParameters(final Action action, final Map<String, Object> parameterMap) {
        SimpleUtil.mustNotNull(action);
        SimpleUtil.mustNotNull(action.getActionName());
        SimpleUtil.mustNotNull(action.getActionDetail());
        boolean invalidResult = action.getActionParameterList().stream().filter(p -> p.getRequired()).filter(p -> !parameterMap.containsKey(p.getParameterName())).findAny().isPresent();
        return !invalidResult;
    }
}
