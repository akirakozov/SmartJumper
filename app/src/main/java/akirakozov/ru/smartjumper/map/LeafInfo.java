package akirakozov.ru.smartjumper.map;

/**
 * Created by akirakozov on 04.06.16.
 */
public class LeafInfo {
    public final LeafState state;
    public final Point coord;
    public final boolean isActive;
    public final boolean isPossibleNewLeaf;


    public LeafInfo(LeafState state, Point coord, boolean isActive, boolean isPossibleNewLeaf) {
        this.state = state;
        this.coord = coord;
        this.isActive = isActive;
        this.isPossibleNewLeaf = isPossibleNewLeaf;
    }
}
