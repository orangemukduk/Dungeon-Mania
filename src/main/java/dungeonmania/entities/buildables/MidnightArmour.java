package dungeonmania.entities.buildables;

import dungeonmania.Game;
import dungeonmania.battles.BattleStatistics;
import dungeonmania.entities.BattleItem;

public class MidnightArmour extends Buildable implements BattleItem {
    private int durability = Integer.MAX_VALUE;
    public static final int MIDNIGHT_ARMOUR_ATTACK = 1;
    public static final int MIDNIGHT_ARMOUR_DEFENCE = 1;

    private int attack = MIDNIGHT_ARMOUR_ATTACK;
    private int defence = MIDNIGHT_ARMOUR_DEFENCE;

    public MidnightArmour(int defence, int attack, int durability) {
        super(null);
        this.defence = defence;
        this.attack = attack;
        this.durability = durability;
    }

    @Override
    public void use(Game game) {
        durability--;
        if (durability <= 0) {
            game.getPlayer().remove(this);
        }
    }

    @Override
    public BattleStatistics applyBuff(BattleStatistics origin) {
        return BattleStatistics.applyBuff(origin, new BattleStatistics(
            0,
            attack,
            defence,
            2,
            1,
            false,
            true));
    }

    @Override
    public int getDurability() {
        return durability;
    }
}
