package star16m.bootsample.resource.service.action;

import org.springframework.stereotype.Component;
import star16m.bootsample.resource.utils.SimpleUtil;

import java.util.*;

@Component
public class ActionProvider {
    private final Map<String, Action> actionMap;

    public ActionProvider() {
        this.actionMap = new HashMap<>();

        addAction(new Action("action-1", ActionType.db, "select id, name, full_name, short_name, create_date, update_date from tb_team", ActionType.db, "insert into tb_team_history(id, name, full_name, short_name, create_date, update_date) values (:id, :name, :full_name, :short_name, :create_date, :update_date)"));
        addAction(new Action("action-2", ActionType.db, "select name, count(name) as team_cnt from tb_team_history group by name", ActionType.db, "insert into tb_team_cnt(name, team_cnt) values (:name, :team_cnt)"));
        addAction(new Action("action-3", ActionType.db, "select id, name, full_name, short_name, create_date, update_date from tb_team", ActionType.csv, "/Users/star16m/Downloads/team.csv"));
        addAction(new Action("action-4", ActionType.db, "select name, count(name) as team_cnt from tb_team_history group by name", ActionType.csv, "/Users/star16m/Downloads/team_cnt.csv"));

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

}
