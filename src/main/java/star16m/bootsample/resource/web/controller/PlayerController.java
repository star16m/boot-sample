package star16m.bootsample.resource.web.controller;

import io.swagger.annotations.Api;
import star16m.bootsample.resource.Resource;
import star16m.bootsample.resource.entity.Player;
import star16m.bootsample.resource.service.PlayerService;
import star16m.bootsample.resource.web.controller.annotations.SimpleRestController;

@SimpleRestController(path="/api/rest/player")
@Api(tags = "Player")
public class PlayerController extends AbstractController<Player, Integer> {
    private PlayerService playerService;
    public PlayerController(PlayerService playerService) {
        super(Resource.PLAYER, playerService);
        this.playerService = playerService;
    }

    // customize
}
