package akirakozov.ru.smartjumper.map;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by akirakozov on 04.06.16.
 */
public class SwampMap {
    public static final int NO_ACTIVE = -1;
    public static final int LEAF_RADIUS = 20;
    public static final int FROG_RADIUS = 50;

    private int activeLeafNumber = NO_ACTIVE;
    private final List<Point> leafPoints = new ArrayList<>();
    private final Map<Integer, LeafState> leafStates;

    private int stepX;
    private int stepY;

    public SwampMap(Map<Integer, LeafState> states) {
        this(new Point(0, 0), states, 0, 0);
    }

    public SwampMap(Point startLeaf, Map<Integer, LeafState> states, int stepX, int stepY) {
        this.activeLeafNumber = -1;
        this.leafStates = states;
        recalcPoints(startLeaf, stepX, stepY);
    }

    public List<LeafInfo> getLeafs() {
        ArrayList<LeafInfo> infos = new ArrayList<>();
        Set<Integer> possibleNewLeafs = findPossibleNewLeafs();
        for (int i = 0; i < leafPoints.size(); i++) {
            LeafState state = leafStates.get(i) == null ?
                    LeafState.EMPTY : leafStates.get(i);
            infos.add(new LeafInfo(
                    state,
                    leafPoints.get(i),
                    i == activeLeafNumber,
                    possibleNewLeafs.contains(i))
            );
        }
        return infos;
    }

    public void recalcPoints(Point startLeaf, int stepX, int stepY) {
        leafPoints.clear();
        this.stepX = stepX;
        this.stepY = stepY;
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                leafPoints.add(new Point(
                        startLeaf.x + 2 * stepX * i,
                        startLeaf.y + 2 * stepY * j)
                );
            }
        }

        Point centerStartLeaf = new Point(startLeaf.x + stepX, startLeaf.y + stepY);
        for (int i = 0; i < 2; i++) {
            for (int j = 0; j < 2; j++) {
                leafPoints.add(new Point(
                        centerStartLeaf.x + 2 * stepX * i,
                        centerStartLeaf.y + 2 * stepY * j)
                );
            }
        }

    }

    public int findLeafByPoint(Point p) {
        for (int i = 0; i < leafPoints.size(); i++) {
            if (isPointInsideLeaf(leafPoints.get(i), p)) {
                return i;
            }
        }
        return NO_ACTIVE;
    }

    public void setActiveLeafByPoint(Point p) {
        int activeNum = findLeafByPoint(p);
        if (leafStates.get(activeNum) != null) {
            activeLeafNumber = activeNum;
        } else {
            activeLeafNumber = NO_ACTIVE;
        }
    }

    public void resetActiveLeaf() {
        activeLeafNumber = NO_ACTIVE;
    }

    public Set<Integer> findPossibleNewLeafs() {
        Set<Integer> newLeafs = new HashSet<>();
        if (activeLeafNumber == NO_ACTIVE) {
            return newLeafs;
        }

        int[] direction = {-1, 0, 1};
        Point active = leafPoints.get(activeLeafNumber);
        for (int dirX : direction) {
            for (int dirY : direction) {
                int mult = dirX == 0 || dirY == 0 ? 2 : 1;
                int x = dirX * mult * stepX + active.x;
                int y = dirY * mult * stepY + active.y;

                int neighbour = findLeafByPoint(new Point(x, y));
                if (leafStates.get(neighbour) == LeafState.GREEN_FROG) {
                    x += dirX * mult * stepX;
                    y += dirY * mult * stepY;
                    int nextNeighbour = findLeafByPoint(new Point(x, y));
                    if (nextNeighbour != NO_ACTIVE && leafStates.get(nextNeighbour) == null) {
                        newLeafs.add(nextNeighbour);
                    }
                }
            }
        }

        return newLeafs;
    }

    public void dropActiveFrog(Point p) {
        if (activeLeafNumber == NO_ACTIVE) {
            return;
        }
        int droppedLeaf = findLeafByPoint(p);
        Set<Integer> possibleNewLeafs = findPossibleNewLeafs();
        if (possibleNewLeafs.contains(droppedLeaf)) {
            Point middlePoint = findMiddlePoint(
                    leafPoints.get(activeLeafNumber),
                    leafPoints.get(droppedLeaf));
            int middleLeafNum = findLeafByPoint(middlePoint);
            leafStates.remove(middleLeafNum);

            leafStates.put(droppedLeaf, leafStates.get(activeLeafNumber));
            leafStates.remove(activeLeafNumber);
        }
    }
    private boolean isPointInsideLeaf(Point leaf, Point p) {
        return leaf.x - FROG_RADIUS <= p.x && p.x <= leaf.x + FROG_RADIUS
                && leaf.y - FROG_RADIUS <= p.y && p.y <= leaf.y + FROG_RADIUS;
    }

    private Point findMiddlePoint(Point p1, Point p2) {
        return new Point((p1.x + p2.x) / 2, (p1.y + p2.y) / 2);
    }
}
