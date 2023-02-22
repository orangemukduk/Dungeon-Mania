package dungeonmania.entities.enemies;

import dungeonmania.Game;
import dungeonmania.entities.Entity;
import dungeonmania.entities.Interactable;
import dungeonmania.entities.Player;
import dungeonmania.map.GameMap;

public abstract class MercenaryState implements Interactable {
    private Mercenary mercenary;

    public MercenaryState(Mercenary mercenary) {
        this.mercenary = mercenary;
    }

    public abstract boolean isAllied();
    public abstract void interact(Player player, Game game);
    public abstract boolean isInteractable(Player player);
    public abstract void move(Game game);
    public abstract void onOverlap(GameMap map, Entity entity);

}
