package dungeonmania.entities.enemies;


import dungeonmania.Game;
import dungeonmania.entities.Entity;
import dungeonmania.entities.Player;
import dungeonmania.entities.buildables.Sceptre;
import dungeonmania.map.GameMap;
import dungeonmania.util.Position;


public class AlliedMercenaryState extends MercenaryState {
    private Position pplayerposition;
    private int mindControlDuration;
    private Mercenary mercenary;
    public AlliedMercenaryState(Mercenary mercenary) {
        super(mercenary);
        this.mercenary = mercenary;

    }


    @Override
    public boolean isAllied() {
        return true;
    }
    public void interact(Player player, Game game) {
        // cannot interact with Allied Mercenary
    }

    public boolean isInteractable(Player player) {
        return false;
    }

    public void move(Game game) {
        Position nextPos;
        GameMap map = game.getMap();
        Player player = game.getPlayer();
        if (mercenary.getCurrentDuration() >= 1 || player.getInventory().count(Sceptre.class) <= 0) {
            mercenary.setCurrentDuration(mercenary.getCurrentDuration() - 1);
            if (isAdjacent(player.getPosition(), mercenary.getPosition())) {
                map.moveTo(mercenary, player.getPreviousPosition());
                this.pplayerposition = player.getPosition();

            } else {
                nextPos = map.dijkstraPathFind(mercenary.getPosition(), map.getPlayerPosition(), mercenary);
                mercenary.isStuck(map, nextPos);
                this.pplayerposition = player.getPosition();
            }
        } else if (mercenary.getCurrentDuration() < 1) {
            mercenary.changeState(new EnemyMercenaryState(mercenary));
        }

    }

    private boolean isAdjacent(Position a, Position b) {
        return (Math.abs(a.getX() - b.getX()) + Math.abs(a.getY() - b.getY())) == 1;
    }

    public void onOverlap(GameMap map, Entity entity) {
        // Nothing occurs on overlap when allied
    }
}
