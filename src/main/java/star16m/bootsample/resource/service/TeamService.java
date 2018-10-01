package star16m.bootsample.resource.service;

import org.springframework.stereotype.Component;
import star16m.bootsample.resource.entity.Team;

@Component
public class TeamService extends AbstractService<Team, Integer> {
    public TeamService(TeamRepository teamRepository) {
        super(teamRepository);
    }
}
