package dungeonmania.mvp;

import dungeonmania.DungeonManiaController;
import dungeonmania.exceptions.InvalidActionException;
import dungeonmania.response.models.DungeonResponse;
import dungeonmania.response.models.EntityResponse;
import dungeonmania.util.Direction;
import dungeonmania.util.Position;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import java.util.List;


public class AssassinTest {
    @Test
    @Tag("17-1")
    @DisplayName("Test assassin in line with Player moves towards them")
    public void simpleMovement() {
        //                                  Wall    Wall   Wall    Wall    Wall    Wall
        // P1       P2      P3      P4      A4      A3      A2      A1      .      Wall
        //                                  Wall    Wall   Wall    Wall    Wall    Wall
        DungeonManiaController dmc = new DungeonManiaController();
        DungeonResponse res = dmc.newGame("d_assassinTest_simpleMovement", "c_assassinTest_simpleMovement");

        assertEquals(new Position(8, 1), getAssassinPos(res));
        res = dmc.tick(Direction.RIGHT);
        assertEquals(new Position(7, 1), getAssassinPos(res));
        res = dmc.tick(Direction.RIGHT);
        assertEquals(new Position(6, 1), getAssassinPos(res));
        res = dmc.tick(Direction.RIGHT);
        assertEquals(new Position(5, 1), getAssassinPos(res));
    }

    @Test
    @Tag("17-2")
    @DisplayName("Testing an assassin can be bribed if it passes bribe rate")
    public void bribePass() {
        //                                                          Wall     Wall     Wall    Wall    Wall
        // P1       P2/Treasure      P3/Treasure    P4/Treasure      A4       A3       A2     A1      Wall
        //                                                          Wall     Wall     Wall    Wall    Wall
        DungeonManiaController dmc = new DungeonManiaController();
        DungeonResponse res = dmc.newGame("d_assassinTest_bribePass", "c_assassinTest_bribePass");

        String assassinId = TestUtils.getEntitiesStream(res, "assassin").findFirst().get().getId();
        List<EntityResponse> entities = res.getEntities();
        assertTrue(TestUtils.countEntityOfType(entities, "assassin") == 1);

        // pick up first treasure
        res = dmc.tick(Direction.RIGHT);
        assertEquals(1, TestUtils.getInventory(res, "treasure").size());
        assertEquals(new Position(7, 1), getAssassinPos(res));

        // pick up second treasure
        res = dmc.tick(Direction.RIGHT);
        assertEquals(2, TestUtils.getInventory(res, "treasure").size());
        assertEquals(new Position(6, 1), getAssassinPos(res));


        // pick up third treasure
        res = dmc.tick(Direction.RIGHT);
        assertEquals(3, TestUtils.getInventory(res, "treasure").size());
        assertEquals(new Position(5, 1), getAssassinPos(res));

        // achieve bribe
        res = assertDoesNotThrow(() -> dmc.interact(assassinId));
        assertEquals(0, TestUtils.getInventory(res, "treasure").size());

        // check if no battles occur
        assertEquals(0, res.getBattles().size());

        // check if assassin still exists
        List<EntityResponse> entities1 = res.getEntities();
        assertTrue(TestUtils.countEntityOfType(entities1, "assassin") == 1);
    }

    @Test
    @Tag("17-3")
    @DisplayName("Testing an assassin cannot be bribed if it doesn't pass bribe rate")
    public void bribeNotPass() {
        //                                                          Wall     Wall     Wall    Wall    Wall
        // P1       P2/Treasure      P3/Treasure    P4/Treasure      A4       A3       A2     A1      Wall
        //                                                          Wall     Wall     Wall    Wall    Wall
        DungeonManiaController dmc = new DungeonManiaController();
        DungeonResponse res = dmc.newGame("d_assassinTest_bribeNotPass", "c_assassinTest_bribeNotPass");
        String assassinId = TestUtils.getEntitiesStream(res, "assassin").findFirst().get().getId();

        // pick up first treasure
        res = dmc.tick(Direction.RIGHT);
        assertEquals(1, TestUtils.getInventory(res, "treasure").size());
        assertEquals(new Position(7, 1), getAssassinPos(res));


        // pick up second treasure
        res = dmc.tick(Direction.RIGHT);
        assertEquals(2, TestUtils.getInventory(res, "treasure").size());
        assertEquals(new Position(6, 1), getAssassinPos(res));


        // pick up third treasure
        res = dmc.tick(Direction.RIGHT);
        assertEquals(3, TestUtils.getInventory(res, "treasure").size());
        assertEquals(new Position(5, 1), getAssassinPos(res));

        // does not achieve bribe
        res = assertDoesNotThrow(() -> dmc.interact(assassinId));
        assertEquals(0, TestUtils.getInventory(res, "treasure").size());

        // check if battle occurs
        assertEquals(1, res.getBattles().size());


        // check if player does not exist anymore
        List<EntityResponse> entities = res.getEntities();
        assertTrue(TestUtils.countEntityOfType(entities, "player") == 0);
    }


    @Test
    @Tag("17-4")
    @DisplayName("Testing a battle does not occur if the player is invisible")
    public void playerInvsible() throws InvalidActionException {
        //                                                          Wall     Wall     Wall    Wall    Wall
        // P1                                       P4/Potion       A4       A3       A2     A1      Wall
        //                                                          Wall     Wall     Wall    Wall    Wall
        DungeonManiaController dmc = new DungeonManiaController();
        DungeonResponse res = dmc.newGame("d_assassinTest_playerInvisible", "c_assassinTest_playerInvisible");

        // check if invisiblity potion is present in map
        // assertEquals(1, TestUtils.getEntities(res, "invisibility_potion").size());
        // assertEquals(0, TestUtils.getInventory(res, "invisibility_potion").size());

        res = dmc.tick(Direction.RIGHT);
        assertEquals(new Position(7, 1), getAssassinPos(res));

        res = dmc.tick(Direction.RIGHT);
        assertEquals(new Position(6, 1), getAssassinPos(res));

        // pick up invisiblity potion
        res = dmc.tick(Direction.RIGHT);
        assertEquals(0, TestUtils.getEntities(res, "invisibility_potion").size());
        assertEquals(1, TestUtils.getInventory(res, "invisibility_potion").size());
        assertEquals(new Position(5, 1), getAssassinPos(res));

        // consume invisiblity potion
        res = dmc.tick(TestUtils.getFirstItemId(res, "invisibility_potion"));
        assertEquals(0, TestUtils.getInventory(res, "invisibility_potion").size());
        assertEquals(0, TestUtils.getEntities(res, "invisibility_potion").size());

        // test no battles occur
        assertEquals(0, res.getBattles().size());
        assertEquals(new Position(4, 1), getAssassinPos(res));

    }

    private Position getAssassinPos(DungeonResponse res) {
        return TestUtils.getEntities(res, "assassin").get(0).getPosition();
    }
}
