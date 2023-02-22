package dungeonmania.entities.enemies;

import dungeonmania.Game;
import dungeonmania.entities.Entity;
import dungeonmania.entities.Player;
import dungeonmania.entities.buildables.Sceptre;
import dungeonmania.entities.collectables.Treasure;
import dungeonmania.map.GameMap;
import dungeonmania.util.Position;


public class EnemyMercenaryState extends MercenaryState {
    private Mercenary mercenary;
    public EnemyMercenaryState(Mercenary mercenary) {
        super(mercenary);
        this.mercenary = mercenary;
    }

    @Override
    public boolean isAllied() {
        return false;
    }

    public void interact(Player player, Game game) {
        if (player.countEntityOfType(Sceptre.class) >= 1) {
            mercenary.changeState(new AlliedMercenaryState(mercenary));
            mercenary.setCurrentDuration(player.getInventory().getFirst(Sceptre.class).getMindControlDuration());

        } else {
            for (int i = 0; i < mercenary.getBribeAmount(); i++) {
                player.use(Treasure.class);
            }
            mercenary.changeState(new AlliedMercenaryState(mercenary));
        }
    }
    public boolean isInteractable(Player player) {
        return (mercenary.getBribeRadius() >= 0
        && player.countEntityOfType(Treasure.class) >= mercenary.getBribeAmount())
        || (player.countEntityOfType(Sceptre.class) >= 1);
    }

    public void move(Game game) {
        Position nextPos;
        GameMap map = game.getMap();

        nextPos = map.dijkstraPathFind(mercenary.getPosition(), map.getPlayerPosition(), mercenary);
        mercenary.isStuck(map, nextPos);
    }

    public void onOverlap(GameMap map, Entity entity) {
    }

    // public void onTick(int tick, Sceptre sceptre) {
    //     if (tick == nextTrigger || nextTrigger >= 0) {
    //         triggerNext(tick, sceptre);
    //     }
    // }

    // public void triggerNext(int currentTick, Sceptre sceptre) {
    //     mercenary.changeState(new EnemyMercenaryState(mercenary));
    //     nextTrigger = currentTick + sceptre.getDuration();
    // }
}

