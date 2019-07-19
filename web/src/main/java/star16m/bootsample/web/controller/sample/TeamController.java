package star16m.bootsample.web.controller.sample;

import io.swagger.annotations.Api;
import star16m.bootsample.web.controller.BaseController;
import star16m.bootsample.web.resource.sample.Resource;
import star16m.bootsample.web.resource.sample.Team;
import star16m.bootsample.web.controller.annotations.SimpleRestController;
import star16m.bootsample.web.service.sample.TeamJpaService;

@SimpleRestController(path = "/api/rest/v1/team")
@Api(tags = "Team")
public class TeamController extends BaseController<Team, Integer> {
    private TeamJpaService teamService;

    public TeamController(TeamJpaService teamService) {
        super(Resource.TEAM, teamService);
        this.teamService = teamService;
    }
}
