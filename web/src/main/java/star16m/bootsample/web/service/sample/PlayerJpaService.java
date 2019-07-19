package star16m.bootsample.web.service.sample;

import org.springframework.stereotype.Component;
import star16m.bootsample.web.resource.sample.Player;
import star16m.bootsample.web.service.BaseJpaService;

import java.util.Map;

@Component
public class PlayerJpaService extends BaseJpaService<Player, Integer> {
    public PlayerJpaService(PlayerRepository playerRepository) {
        super(playerRepository);
    }

    @Override
    public void patchedObject(Player o, Map<String, Object> map) {
        if (map.containsKey("backnumber")) o.setBackNumber((String) map.get("backnumber"));
    }
}
