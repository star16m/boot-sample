package star16m.bootsample.web.service.sample;

import org.springframework.stereotype.Component;
import star16m.bootsample.web.resource.sample.Team;
import star16m.bootsample.web.service.BaseJpaService;

import java.util.Map;

@Component
public class TeamJpaService extends BaseJpaService<Team, Integer> {
    public TeamJpaService(TeamRepository teamRepository) {
        super(teamRepository);
    }

    @Override
    public void patchedObject(Team o, Map<String, Object> map) {
        if (map.containsKey("fullName")) o.setFullName((String) map.get("fullName"));
        if (map.containsKey("shortName")) o.setShortName((String) map.get("shortName"));
    }
}
