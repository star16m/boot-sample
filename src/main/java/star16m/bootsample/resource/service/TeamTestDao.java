package star16m.bootsample.resource.service;

import org.apache.ibatis.annotations.Mapper;
import star16m.bootsample.resource.entity.TeamTest;

import java.util.List;

@Mapper
public interface TeamTestDao {
    List<TeamTest> findAll();

    void create(TeamTest teamTest);
}
