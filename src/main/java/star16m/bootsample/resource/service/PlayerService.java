package star16m.bootsample.resource.service;

import org.springframework.stereotype.Component;
import star16m.bootsample.resource.entity.Player;

@Component
public class PlayerService extends AbstractService<Player, Integer> {
    public PlayerService(PlayerRepository playerRepository) {
        super(playerRepository);
    }
}
