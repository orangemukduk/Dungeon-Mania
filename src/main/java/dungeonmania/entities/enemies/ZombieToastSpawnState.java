package dungeonmania.entities.enemies;

import dungeonmania.Game;
import dungeonmania.entities.Interactable;
import dungeonmania.entities.Player;
import dungeonmania.map.GameMap;

public abstract class ZombieToastSpawnState implements Interactable {
    private ZombieToastSpawner spawner;

    public ZombieToastSpawnState(ZombieToastSpawner spawner) {
        this.spawner = spawner;
    }

    public abstract void spawn(Game game);
    public abstract void onDestroy(GameMap map);
    public abstract void interact(Player player, Game game);
    public abstract boolean isInteractable(Player player);
}
