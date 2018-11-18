package star16m.bootsample.resource.service;

import org.springframework.stereotype.Component;
import star16m.bootsample.resource.entity.Team;

import java.util.Map;

@Component
public class TeamService extends BaseService<Team, Integer> {
    public TeamService(TeamRepository teamRepository) {
        super(teamRepository);
    }

    @Override
    protected void patchedObject(Team o, Map<String, Object> map) {
        if (map.containsKey("fullName")) o.setFullName((String) map.get("fullName"));
        if (map.containsKey("shortName")) o.setShortName((String) map.get("shortName"));
    }
}
