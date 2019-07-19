package star16m.bootsample.web.service.task;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import star16m.bootsample.web.resource.sample.Team;
import star16m.bootsample.web.service.sample.TeamJpaService;

import java.util.List;

@Slf4j
@Component("TEAM")
@Scope("prototype")
public class TeamTask extends ActionTask {
    private TeamJpaService teamService;
    public TeamTask(TeamJpaService teamService) {
        super("TeamTask");
        this.teamService = teamService;
    }
    @Override
    public void execute() {
        List<Team> teamList = teamService.findAll();
        teamList.stream().forEach(team -> log.info("team info [{}]", team));
    }
}
