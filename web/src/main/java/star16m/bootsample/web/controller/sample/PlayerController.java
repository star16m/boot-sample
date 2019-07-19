package star16m.bootsample.web.controller.sample;

import io.swagger.annotations.Api;
import star16m.bootsample.web.controller.BaseController;
import star16m.bootsample.web.resource.sample.Player;
import star16m.bootsample.web.resource.sample.Resource;
import star16m.bootsample.web.controller.annotations.SimpleRestController;
import star16m.bootsample.web.service.sample.PlayerJpaService;

@SimpleRestController(path = "/api/rest/v1/player", summary = "야구선수 API")
@Api(tags = { "Player" })
public class PlayerController extends BaseController<Player, Integer> {
    private PlayerJpaService playerService;

    public PlayerController(PlayerJpaService playerService) {
        super(Resource.PLAYER, playerService);
        this.playerService = playerService;
    }

    // customize
}
