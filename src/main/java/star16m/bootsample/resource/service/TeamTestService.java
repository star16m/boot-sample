package star16m.bootsample.resource.service;

import org.springframework.stereotype.Service;
import star16m.bootsample.resource.entity.TeamTest;

import java.util.List;

@Service
public class TeamTestService {
    private final TeamTestDao teamTestDao;
    public TeamTestService(TeamTestDao teamTestDao) {
        this.teamTestDao = teamTestDao;
    }
    public List<TeamTest> findAll() {
        return this.teamTestDao.findAll();
    }

    public void create(TeamTest teamTest) {
        this.teamTestDao.create(teamTest);
    }
}
