package star16m.bootsample.resource.service;

import org.springframework.stereotype.Component;
import star16m.bootsample.resource.entity.Player;

import java.util.Map;

@Component
public class PlayerService extends AbstractService<Player, Integer> {
    public PlayerService(PlayerRepository playerRepository) {
        super(playerRepository);
    }

    @Override
    protected void patchedObject(Player o, Map<String, Object> map) {
        if (map.containsKey("backnumber")) o.setBackNumber((Integer)map.get("backnumber"));
    }
}
