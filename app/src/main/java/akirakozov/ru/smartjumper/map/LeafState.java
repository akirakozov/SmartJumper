package akirakozov.ru.smartjumper.map;

/**
 * Created by akirakozov on 04.06.16.
 */
public enum LeafState {
    EMPTY(0xFF000066, SwampMap.LEAF_RADIUS),
    GREEN_FROG(0xFF006600, SwampMap.FROG_RADIUS),
    RED_FROG(0xFF660000, SwampMap.FROG_RADIUS);

    public final int color;
    public final int radius;

    LeafState(int color, int radius) {
        this.color = color;
        this.radius = radius;
    }
}
