package star16m.bootsample.resource.web.controller;

import io.swagger.annotations.Api;
import star16m.bootsample.resource.Resource;
import star16m.bootsample.resource.entity.Player;
import star16m.bootsample.resource.service.PlayerService;
import star16m.bootsample.resource.web.controller.annotations.SimpleRestController;
import star16m.bootsample.resource.web.controller.annotations.SimpleRestMethod;

@SimpleRestController(path = "/api/rest/v1/player", supportedMethod = {SimpleRestMethod.FIND_ALL})
@Api(tags = { "Player" })
public class PlayerController extends BaseController<Player, Integer> {
    private PlayerService playerService;

    public PlayerController(PlayerService playerService) {
        super(Resource.PLAYER, playerService);
        this.playerService = playerService;
    }

    // customize
}
