package star16m.bootsample.resource.web.controller;

import star16m.bootsample.resource.entity.Team;
import star16m.bootsample.resource.service.TeamService;
import star16m.bootsample.resource.web.controller.annotations.SimpleRestController;

@SimpleRestController(path="/api/rest/team")
public class TeamController extends AbstractController<Team, Integer> {
    private TeamService teamService;
    public TeamController(TeamService teamService) {
        super("team", teamService);
        this.teamService = teamService;
    }

    // customize
}
