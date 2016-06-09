package akirakozov.ru.smartjumper.map;

import junit.framework.Assert;

import org.junit.Test;

import java.util.HashMap;

import static org.junit.Assert.*;

/**
 * Created by akirakozov on 05.06.16.
 */
public class SwampMapTest {

    @Test
    public void testFindPossibleNewLeafs() throws Exception {
        HashMap<Integer, LeafState> states = new HashMap<>();
        states.put(3, LeafState.GREEN_FROG);
        states.put(0, LeafState.GREEN_FROG);

        Point startP = new Point(50, 50);
        SwampMap smap = new SwampMap(startP, states, 100, 100);
        smap.setActiveLeafByPoint(startP);

        Assert.assertEquals(1, smap.findPossibleNewLeafs().size());
        Assert.assertEquals(Integer.valueOf(6), smap.findPossibleNewLeafs().iterator().next());
    }
}