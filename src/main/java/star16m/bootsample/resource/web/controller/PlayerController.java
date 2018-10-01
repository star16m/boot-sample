package star16m.bootsample.resource.web.controller;

import star16m.bootsample.resource.entity.Player;
import star16m.bootsample.resource.service.PlayerService;
import star16m.bootsample.resource.web.controller.annotations.SimpleRestController;

@SimpleRestController(path="/api/rest/player")
public class PlayerController extends AbstractController<Player, Integer> {
    private PlayerService playerService;
    public PlayerController(PlayerService playerService) {
        super("player", playerService, true);
        this.playerService = playerService;
    }

    // customize
}
