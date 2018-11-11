package star16m.bootsample.resource.web.controller;

import io.swagger.annotations.Api;
import star16m.bootsample.resource.Resource;
import star16m.bootsample.resource.entity.Team;
import star16m.bootsample.resource.service.TeamService;
import star16m.bootsample.resource.web.controller.annotations.ApiVersion;
import star16m.bootsample.resource.web.controller.annotations.SimpleRestController;

@SimpleRestController(path = "/api/{v}/rest/team")
@ApiVersion
@Api(tags = "Team")
public class TeamController extends AbstractController<Team, Integer> {
    private TeamService teamService;

    public TeamController(TeamService teamService) {
        super(Resource.TEAM, teamService);
        this.teamService = teamService;
    }

    // customize
}
