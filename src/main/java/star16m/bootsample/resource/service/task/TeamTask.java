package star16m.bootsample.resource.service.task;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import star16m.bootsample.resource.entity.Team;
import star16m.bootsample.resource.service.TeamService;

import java.util.List;

@Component("TEAM")
@Scope("prototype")
public class TeamTask extends ActionTask {
    private TeamService teamService;
    public TeamTask(TeamService teamService) {
        super("TeamTask");
        this.teamService = teamService;
    }
    @Override
    public void execute() {
        System.out.println("[" + getId() + "-" + getName() + "] this is teamtask");
        List<Team> teamList = teamService.findAll();

        teamList.stream().forEach(team -> System.out.println(team));
    }
}
