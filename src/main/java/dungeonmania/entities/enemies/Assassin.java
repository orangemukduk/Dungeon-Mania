package dungeonmania.entities.enemies;

import dungeonmania.Game;
import dungeonmania.entities.Player;
import dungeonmania.entities.buildables.Sceptre;
import dungeonmania.entities.collectables.Treasure;
import dungeonmania.util.Position;
import java.util.Random;


public class Assassin extends Mercenary {
    public static final int ASSASSIN_BRIBE_AMOUNT = 1;
    public static final int DEFAULT_BRIBE_RADIUS = 1;
    public static final double ASSASSIN_BRIBE_FAIL_RATE = 0;
    public static final double ASSASSIN_HEALTH = 5;
    public static final double ASSASSIN_ATTACK = 2;



    private int bribeAmount = Assassin.DEFAULT_BRIBE_AMOUNT;
    private double bribeRate = Assassin.ASSASSIN_BRIBE_FAIL_RATE;

    public Assassin(Position position, double health, double attack, int bribeAmount, int bribeRadius,
    double bribeRate) {
        super(position, health, attack, bribeAmount, bribeRadius);
        this.bribeRate = bribeRate;
        this.bribeAmount = bribeAmount;
    }

    @Override
    public void interact(Player player, Game game) {
        if (player.countEntityOfType(Sceptre.class) >= 1) {
            this.changeState(new AlliedMercenaryState(this));
            this.setCurrentDuration(player.getInventory().getFirst(Sceptre.class).getMindControlDuration());
        } else {
            Random random = new Random();
            if (isInteractable(player)) {
                if (random.nextDouble() > bribeRate) {
                    this.changeState(new AlliedMercenaryState(this));
                }
            }
            for (int i = 0; i < bribeAmount; i++) {
                player.use(Treasure.class);
            }
        }

    }

}
