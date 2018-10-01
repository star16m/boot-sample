package star16m.bootsample.resource;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import star16m.bootsample.resource.entity.Team;
import star16m.bootsample.resource.service.TeamService;

import java.util.List;

@Component
public class SimpleBatch implements CommandLineRunner {

    private TeamService yagooService;

    public SimpleBatch(TeamService yagooService) {
        this.yagooService = yagooService;
    }
    @Override
    public void run(String... args) throws Exception {
        System.out.println("RUNNING at BatchTTT");

        Team team = new Team();
        team.setId(2);
        team.setName("롯데");
        team.setFullName("롯데 자이언츠");
        team.setShortName("LOTTE");
        this.yagooService.save(team);

        List<Team> teams = this.yagooService.findAll();
        teams.stream().forEach(t -> {
            System.out.println(t);
        });

    }
}
