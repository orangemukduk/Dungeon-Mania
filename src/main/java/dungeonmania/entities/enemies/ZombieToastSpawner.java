package dungeonmania.entities.enemies;

import dungeonmania.Game;
import dungeonmania.entities.Entity;
import dungeonmania.entities.Interactable;
import dungeonmania.entities.Player;
import dungeonmania.map.GameMap;
import dungeonmania.util.Position;

public class ZombieToastSpawner extends Entity implements Interactable {
    public static final int DEFAULT_SPAWN_INTERVAL = 0;
    private ZombieToastSpawnState state;

    public ZombieToastSpawner(Position position, int spawnInterval) {
        super(position);
        this.state = new SpawnerAliveState(this);
    }

    public void spawn(Game game) {
        state.spawn(game);
    }

    // @Override
    public void onDestroy(GameMap map) {
        state.onDestroy(map);
    }

    @Override
    public void interact(Player player, Game game) {
        state.interact(player, game);
    }

    @Override
    public boolean isInteractable(Player player) {
        return state.isInteractable(player);
    }

    // @Override
    // public void onOverlap(GameMap map, Entity entity) {
    //     return;
    // }

    // @Override
    // public void onMovedAway(GameMap map, Entity entity) {
    //     return;
    // }
}
