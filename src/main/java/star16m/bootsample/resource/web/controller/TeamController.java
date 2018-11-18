package star16m.bootsample.resource.web.controller;

import io.swagger.annotations.Api;
import star16m.bootsample.resource.Resource;
import star16m.bootsample.resource.entity.Team;
import star16m.bootsample.resource.service.TeamService;
import star16m.bootsample.resource.web.controller.annotations.SimpleRestController;

@SimpleRestController(path = "/api/v1/rest/team")
@Api(tags = "Team")
public class TeamController extends BaseController<Team, Integer> {
    private TeamService teamService;

    public TeamController(TeamService teamService) {
        super(Resource.TEAM, teamService);
        this.teamService = teamService;
    }
}
