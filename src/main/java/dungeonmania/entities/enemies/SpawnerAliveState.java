package dungeonmania.entities.enemies;

import dungeonmania.Game;
import dungeonmania.entities.Player;
import dungeonmania.map.GameMap;
import dungeonmania.util.Position;

public class SpawnerAliveState extends ZombieToastSpawnState {
    private ZombieToastSpawner spawner;
    public SpawnerAliveState(ZombieToastSpawner spawner) {
        super(spawner);
        this.spawner = spawner;
    }

    public void spawn(Game game) {
        game.getEntityFactory().spawnZombie(game, spawner);
    }

    @Override
    public void onDestroy(GameMap map) {
        Game g = map.getGame();
        g.unsubscribe(spawner.getId());
    }

    @Override
    public void interact(Player player, Game game) {
        player.getWeapon().use(game);
        spawner.changeState(new SpawnerDeadState(spawner));
    }

    @Override
    public boolean isInteractable(Player player) {
        return Position.isAdjacent(player.getPosition(), spawner.getPosition()) && player.hasWeapon();
    }


}
