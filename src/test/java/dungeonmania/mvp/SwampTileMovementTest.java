package dungeonmania.mvp;

import dungeonmania.DungeonManiaController;
import dungeonmania.response.models.DungeonResponse;
import dungeonmania.util.Direction;
import dungeonmania.util.Position;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;


public class SwampTileMovementTest {

    @Test
    @DisplayName("Make sure player and ally movement is not interfered with")
    public void playerAllySwampTile() {
        DungeonManiaController dmc = new DungeonManiaController();
        DungeonResponse res = dmc.newGame("d_swampTileTest", "c_swampTileTest");

        String mercId = TestUtils.getEntitiesStream(res, "mercenary").findFirst().get().getId();
        //assertEquals(new Position(1, 2), (getMercPos(res)));
        //assertEquals(new Position(1, 2), (getPlayerPos(res)));
        // Player should pick up treasure then bribe the mercenary
        res = dmc.tick(Direction.DOWN);
        //assertEquals(new Position(1, 2), (getMercPos(res)));
        assertEquals(1, TestUtils.getInventory(res, "treasure").size());
        res = assertDoesNotThrow(() -> dmc.interact(mercId));
        assertEquals(0, TestUtils.getInventory(res, "treasure").size());
        assertEquals(new Position(1, 2), (getPlayerPos(res)));
        assertEquals(new Position(1, 1), (getMercPos(res)));
        // After mercenary has been bribed he should be adjacent to the player and an ally
        res = dmc.tick(Direction.DOWN);
        assertTrue(new Position(1, 3).equals(getPlayerPos(res)));
        assertTrue(new Position(1, 2).equals(getMercPos(res)));
        // Now that the player is away from the swamp and mercenary is behind him
        res = dmc.tick(Direction.DOWN);
        assertTrue(new Position(1, 4).equals(getPlayerPos(res)));
        assertTrue(new Position(1, 3).equals(getMercPos(res)));
        // Now the player but not the ally should be off the tile without any interference
        res = dmc.tick(Direction.DOWN);
        assertTrue(new Position(1, 5).equals(getPlayerPos(res)));
        assertTrue(new Position(1, 3).equals(getMercPos(res)));
        // making the player adjacent to the ally should move him off the tile to the players previous position
        res = dmc.tick(Direction.UP);
        assertTrue(new Position(1, 4).equals(getPlayerPos(res)));
        assertTrue(new Position(1, 5).equals(getMercPos(res)));
        res = dmc.tick(Direction.DOWN);
        // Now that they are on top of one another djikstra should take over
        assertTrue(new Position(1, 5).equals(getPlayerPos(res)));
        assertTrue(new Position(1, 5).equals(getMercPos(res)));
    }

    @Test
    @DisplayName("Make sure enemies stay on swamp tile for 2 ticks is not interfered with")
    public void enemySwampTile() {
        DungeonManiaController dmc = new DungeonManiaController();
        DungeonResponse res = dmc.newGame("d_swampTileTest1", "c_swampTileTest");

        // first tick to get onto the tile
        res = dmc.tick(Direction.LEFT);
        assertEquals(new Position(1, 1), getSpiderPos(res));
        assertTrue(new Position(0, -1).equals(getToastPos(res)) || new Position(2, -1).equals(getToastPos(res))
        || new Position(1, -2).equals(getToastPos(res)) || new Position(1, 0).equals(getToastPos(res)));
        assertEquals(new Position(1, 4), getAssPos(res));
        // second tick is on tile
        res = dmc.tick(Direction.LEFT);
        assertTrue(new Position(1, 1).equals(getSpiderPos(res)));
        assertTrue(new Position(0, -1).equals(getToastPos(res)) || new Position(2, -1).equals(getToastPos(res))
        || new Position(1, -2).equals(getToastPos(res)) || new Position(1, 0).equals(getToastPos(res)));
        assertEquals(new Position(1, 4), getAssPos(res));
        // third tick is on tile
        res = dmc.tick(Direction.LEFT);
        assertTrue(new Position(1, 1).equals(getSpiderPos(res)));
        assertTrue(new Position(0, -1).equals(getToastPos(res)) || new Position(2, -1).equals(getToastPos(res))
        || new Position(1, -2).equals(getToastPos(res)) || new Position(1, 0).equals(getToastPos(res)));
        assertEquals(new Position(1, 4), getAssPos(res));
        Position oldToast = getToastPos(res);
        // Fourth tick all entities should be off the tile
        res = dmc.tick(Direction.LEFT);
        assertFalse(new Position(1, 1).equals(getSpiderPos(res)));
        assertFalse(oldToast == getToastPos(res));
        assertFalse(new Position(1, 4).equals(getAssPos(res)));
    }
    private Position getMercPos(DungeonResponse res) {
        return TestUtils.getEntities(res, "mercenary").get(0).getPosition();
    }

    private Position getPlayerPos(DungeonResponse res) {
        return TestUtils.getEntities(res, "player").get(0).getPosition();
    }
    private Position getSpiderPos(DungeonResponse res) {
        return TestUtils.getEntities(res, "spider").get(0).getPosition();
    }
    private Position getToastPos(DungeonResponse res) {
        return TestUtils.getEntities(res, "zombie_toast").get(0).getPosition();
    }
    private Position getAssPos(DungeonResponse res) {
        return TestUtils.getEntities(res, "assassin").get(0).getPosition();
    }

}
