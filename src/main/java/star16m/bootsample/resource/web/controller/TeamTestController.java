package star16m.bootsample.resource.web.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import star16m.bootsample.resource.Resource;
import star16m.bootsample.resource.config.ResultCode;
import star16m.bootsample.resource.config.ResultMessage;
import star16m.bootsample.resource.config.SwaggerOperation;
import star16m.bootsample.resource.entity.Team;
import star16m.bootsample.resource.entity.TeamTest;
import star16m.bootsample.resource.service.TeamService;
import star16m.bootsample.resource.service.TeamTestService;
import star16m.bootsample.resource.web.controller.annotations.SimpleRestController;

import java.util.List;

@RestController
@Api(tags = "TeamTest")
public class TeamTestController {
    private TeamTestService teamTestService;

    public TeamTestController(TeamTestService teamTestService) {
        this.teamTestService = teamTestService;
    }

    @GetMapping(value = "/api/${app.version}/rest/teamtest")
    public List<TeamTest> findAll() {
        return this.teamTestService.findAll();
    }


    @SwaggerOperation(value = {
        @ResultMessage(code = ResultCode.SUCCESS, description = "성공시"),
        @ResultMessage(code = ResultCode.FAIL, description = "만약 이런저런 상황에서 이런 저런 parameter 가 입력되면 이런 저런 상황에 의해 해당 에러가 발생할 수 있습니다.만약 이런저런 상황에서 이런 저런 parameter 가 입력되면 이런 저런 상황에 의해 해당 에러가 발생할 수 있습니다.만약 이런저런 상황에서 이런 저런 parameter 가 입력되면 이런 저런 상황에 의해 해당 에러가 발생할 수 있습니다."),
    })
    @PostMapping(value = "/api/${app.version}/rest/teamtest")
    public ResponseEntity<String> create(@RequestBody TeamTest teamTest) {
        this.teamTestService.create(teamTest);
        return ResponseEntity.ok("success");
    }
}
