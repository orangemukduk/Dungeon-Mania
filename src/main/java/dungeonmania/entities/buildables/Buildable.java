package dungeonmania.entities.buildables;

import dungeonmania.entities.Entity;
import dungeonmania.entities.inventory.InventoryItem;
import dungeonmania.util.Position;

public class Buildable extends Entity implements InventoryItem, BattleItem {

    public Buildable(Position position) {
        super(position);
    }

    // @Override
    // public void onOverlap(GameMap map, Entity entity) {
    //     return;
    // }

    // @Override
    // public void onMovedAway(GameMap map, Entity entity) {
    //     return;
    // }

    // @Override
    // public void onDestroy(GameMap gameMap) {
    //     return;
    // }
}
