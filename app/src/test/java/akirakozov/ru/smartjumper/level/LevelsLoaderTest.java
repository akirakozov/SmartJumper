package akirakozov.ru.smartjumper.level;

import org.junit.Assert;
import org.junit.Test;

import java.util.HashMap;

import akirakozov.ru.smartjumper.map.LeafState;

/**
 * Created by akirakozov on 13.06.16.
 */
public class LevelsLoaderTest {

    @Test
    public void storeLoadLevels() throws Exception {
        Levels levels = new Levels();
        levels.addLevel(LevelType.EASY, createLevel());

        String res = LevelsLoader.storeLevels(levels);
        Levels result = LevelsLoader.readLevels(res);
        Assert.assertEquals(levels, result);
    }

    private Levels.Level createLevel() {
        HashMap<Integer, LeafState> states = new HashMap<>();
        states.put(3, LeafState.GREEN_FROG);
        states.put(5, LeafState.RED_FROG);
        states.put(4, LeafState.GREEN_FROG);
        states.put(10, LeafState.GREEN_FROG);
        states.put(2, LeafState.GREEN_FROG);
        states.put(0, LeafState.GREEN_FROG);

        return new Levels.Level(states);
    }
}
