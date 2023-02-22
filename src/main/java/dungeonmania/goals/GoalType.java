package dungeonmania.goals;

import dungeonmania.Game;

public abstract class GoalType {
    public abstract boolean achieved(Game game, Goal goal1, Goal goal2, int target);
    public abstract String toString(Game game, Goal goal1, Goal goal2);
}
