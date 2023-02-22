package dungeonmania.mvp;

import dungeonmania.DungeonManiaController;
import dungeonmania.response.models.DungeonResponse;
import dungeonmania.util.Direction;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class EnemiesDestroyedGoals {
    @Test
    @DisplayName("testing enemies destroyed but not spawners")
    public void spawnersNoEnemies() {
        DungeonManiaController dmc;
        dmc = new DungeonManiaController();
        DungeonResponse res = dmc.newGame("d_basicGoalsTest_spawner", "OP_player");

        assertTrue(TestUtils.getGoals(res).contains(":enemies"));
        // move player to sword
        res = dmc.tick(Direction.DOWN);
        res = dmc.tick(Direction.LEFT);
        res = dmc.tick(Direction.LEFT);
        // Sword is in inventory
        assertEquals(1, TestUtils.getInventory(res, "sword").size());

        // move player to spawner
        res = dmc.tick(Direction.DOWN);

        // interact with spawner
        String spawnerId = TestUtils.getEntities(res, "zombie_toast_spawner").get(0).getId();
        res = assertDoesNotThrow(() -> dmc.interact(spawnerId));

        // despite spawner being destroyed, no enemies were killed so the goal is unfulfilled
        assertTrue(TestUtils.getGoals(res).contains(":enemies"));

    }

    @Test
    @DisplayName("Testing no spawners but enemies destroyed")
    public void enemiesNoSpawners() {
        DungeonManiaController dmc;
        dmc = new DungeonManiaController();
        DungeonResponse res = dmc.newGame("d_basicGoalsTest_enemy", "OP_player");

        assertTrue(TestUtils.getGoals(res).contains(":enemies"));
        // move player to sword
        res = dmc.tick(Direction.DOWN);
        res = dmc.tick(Direction.LEFT);
        res = dmc.tick(Direction.LEFT);
        // goals should be complete as default value is 1 enemy defeated

        assertEquals("", TestUtils.getGoals(res));
    }

    @Test
    @DisplayName("Testing no spawners but alot of enemies destroyed")
    public void manyEnemiesNoSpawners() {
        DungeonManiaController dmc;
        dmc = new DungeonManiaController();
        DungeonResponse res = dmc.newGame("d_basicEnemiesTest", "OP_player2");

        assertTrue(TestUtils.getGoals(res).contains(":enemies"));
        // move player to enemy mercenary
        res = dmc.tick(Direction.LEFT);
        // after one mercenary ahs been killed the goal should still exist
        assertTrue(TestUtils.getGoals(res).contains(":enemies"));

        res = dmc.tick(Direction.LEFT);
        // goals should be complete as all 3 are defeated (the enemies_goal given in the config)

        assertEquals("", TestUtils.getGoals(res));
    }

    @Test
    @DisplayName("Testing conjuntion tests with enemies")
    public void manyEnemiesNoSpawnersManyGoals() {
        DungeonManiaController dmc;
        dmc = new DungeonManiaController();
        DungeonResponse res = dmc.newGame("d_complexEnemiesGoals1", "OP_player");

        assertTrue(TestUtils.getGoals(res).contains(":enemies"));
        assertTrue(TestUtils.getGoals(res).contains(":exit"));
        assertTrue(TestUtils.getGoals(res).contains(":treasure"));
        assertTrue(TestUtils.getGoals(res).contains(":boulders"));
        // move player to enemy spider
        res = dmc.tick(Direction.RIGHT);
        assertFalse(TestUtils.getGoals(res).contains(":enemies"));
        // move player to treasure
        res = dmc.tick(Direction.RIGHT);
        assertFalse(TestUtils.getGoals(res).contains(":treasure"));

        // move player to boulder on switch
        res = dmc.tick(Direction.RIGHT);
        res = dmc.tick(Direction.RIGHT);
        res = dmc.tick(Direction.DOWN);
        res = dmc.tick(Direction.DOWN);
        res = dmc.tick(Direction.DOWN);
        res = dmc.tick(Direction.LEFT);
        // goals should be complete as all 3 are done
        assertFalse(TestUtils.getGoals(res).contains(":boulders"));
        // all goals complete as exit does not have to be complete in OR action
        assertEquals("", TestUtils.getGoals(res));

    }

    @Test
    @DisplayName("Testing conjuntion and order tests with enemies and exit goals")
    public void enemiesWithExitManyGoals() {
        DungeonManiaController dmc;
        dmc = new DungeonManiaController();
        DungeonResponse res = dmc.newGame("d_complexExitGoalsTest", "OP_player");

        assertTrue(TestUtils.getGoals(res).contains(":enemies"));
        assertTrue(TestUtils.getGoals(res).contains(":exit"));
        assertTrue(TestUtils.getGoals(res).contains(":treasure"));
        // move player to enemy spider
        res = dmc.tick(Direction.RIGHT);
        assertFalse(TestUtils.getGoals(res).contains(":enemies"));
        // move player to treasure
        res = dmc.tick(Direction.RIGHT);
        assertFalse(TestUtils.getGoals(res).contains(":treasure"));

        // move player to boulder on switch
        res = dmc.tick(Direction.RIGHT);
        res = dmc.tick(Direction.RIGHT);
        res = dmc.tick(Direction.DOWN);
        res = dmc.tick(Direction.DOWN);
        res = dmc.tick(Direction.DOWN);
        res = dmc.tick(Direction.LEFT);
        // exit still needs to be complete
        assertTrue(TestUtils.getGoals(res).contains(":exit"));
        res = dmc.tick(Direction.UP);
        res = dmc.tick(Direction.LEFT);
        // all goals complete as exit does not have to be complete in OR action
        assertEquals("", TestUtils.getGoals(res));

    }

}
