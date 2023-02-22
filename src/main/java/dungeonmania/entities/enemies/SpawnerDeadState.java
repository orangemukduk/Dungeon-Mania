package dungeonmania.entities.enemies;

import dungeonmania.Game;
import dungeonmania.entities.Player;
import dungeonmania.map.GameMap;

public class SpawnerDeadState extends ZombieToastSpawnState {

    public SpawnerDeadState(ZombieToastSpawner spawner) {
        super(spawner);
    }

    @Override
    public void spawn(Game game) {
        // does not spawn anything
    }

    @Override
    public void onDestroy(GameMap map) {
        // cant be destroyed
    }

    @Override
    public void interact(Player player, Game game) {
        // cant be interacted
    }

    @Override
    public boolean isInteractable(Player player) {
        return false;
    }

}
