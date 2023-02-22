package dungeonmania.entities;

import dungeonmania.map.GameMap;
import dungeonmania.util.Position;

public class SwampTile extends Entity {
    private int stuckTime;

    public SwampTile(Position position, int stuckTime) {
        super(position);
        this.stuckTime = stuckTime;
    }

    @Override
    public void onOverlap(GameMap map, Entity entity) {
        if (entity.getStuckTime() <= 0) {
            entity.setStuckTime(stuckTime);
        }
    }
    @Override
    public boolean canMoveOnto(GameMap map, Entity entity) {
        return true;
    }
}
