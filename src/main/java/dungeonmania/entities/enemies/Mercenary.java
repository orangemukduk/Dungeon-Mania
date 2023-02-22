package dungeonmania.entities.enemies;

import java.util.List;

import dungeonmania.Game;
import dungeonmania.entities.Entity;
import dungeonmania.entities.Interactable;
import dungeonmania.entities.Player;
import dungeonmania.map.GameMap;
import dungeonmania.util.Position;

public class Mercenary extends Enemy implements Interactable {
    public static final int DEFAULT_BRIBE_AMOUNT = 1;
    public static final int DEFAULT_BRIBE_RADIUS = 1;
    public static final double DEFAULT_ATTACK = 10.0;
    public static final double DEFAULT_HEALTH = 10.0;
    private int mindControlDuration = 0;

    private int bribeAmount = Mercenary.DEFAULT_BRIBE_AMOUNT;
    private int bribeRadius = Mercenary.DEFAULT_BRIBE_RADIUS;
    private Position position;


    private MercenaryState state;

    public Mercenary(Position position, double health, double attack, int bribeAmount, int bribeRadius) {
        super(position, health, attack);
        this.bribeAmount = bribeAmount;
        this.bribeRadius = bribeRadius;
        this.position = getPosition();
        this.state = new EnemyMercenaryState(this);
    }

    public void changeState(MercenaryState state) {
        this.state = state;
    }

    @Override
    public void onOverlap(GameMap map, Entity entity) {
        if (isAllied()) return;
        super.onOverlap(map, entity);
    }

    public boolean isAllied() {
        return state.isAllied();
    }

    @Override
    public void interact(Player player, Game game) {
        state.interact(player, game);
    }

    @Override
    public void move(Game game) {
        state.move(game);
    }

    @Override
    public boolean isInteractable(Player player) {
        return state.isInteractable(player);
    }

    public int getBribeRadius() {
        return bribeRadius;
    }

    public int getBribeAmount() {
        return bribeAmount;
    }

    public List<Position> getCardinallyAdjacentPositions() {
        return position.getCardinallyAdjacentPositions();
    }

    public void setCurrentDuration(int mindControlDuration) {
        this.mindControlDuration = mindControlDuration;
    }

    public int getCurrentDuration() {
        return mindControlDuration;
    }
}

