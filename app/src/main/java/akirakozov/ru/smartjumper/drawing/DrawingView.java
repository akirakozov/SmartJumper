package akirakozov.ru.smartjumper.drawing;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.view.MotionEvent;
import android.view.View;

import java.util.HashMap;

import akirakozov.ru.smartjumper.map.LeafInfo;
import akirakozov.ru.smartjumper.map.LeafState;
import akirakozov.ru.smartjumper.map.Point;
import akirakozov.ru.smartjumper.map.SwampMap;

/**
 * Created by akirakozov on 04.06.16.
 */
public class DrawingView extends View {

    private final SwampMap swampMap;
    private boolean isActiveFrogMoving;
    private Point activeFrogMovingCoord;

    public DrawingView(Context context) {
        super(context);
        this.swampMap = createSwampMap();
        this.isActiveFrogMoving = false;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        Paint paint = new Paint();
        paint.setStyle(Paint.Style.FILL);
        for (LeafInfo l : swampMap.getLeafs()) {
            int color = l.state.color;
            int r = l.state.radius;
            int x = l.coord.x;
            int y = l.coord.y;
            if (l.isActive) {
                color = 0xFF555500;
                if (isActiveFrogMoving) {
                    x = activeFrogMovingCoord.x;
                    y = activeFrogMovingCoord.y;
                }
            } else if (l.isPossibleNewLeaf) {
                color = 0xFF550055;
                r = SwampMap.FROG_RADIUS;
            }
            paint.setColor(color);
            canvas.drawCircle(x, y, r, paint);
        }
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        int edge = Math.min(w, h) / 12;
        swampMap.recalcPoints(new Point(edge, edge), (int) (w * 0.8 / 4), (int) (h * 0.8 / 4));
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        Point p = new Point((int)event.getX(), (int)event.getY());
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                isActiveFrogMoving = true;
                activeFrogMovingCoord = p;
                swampMap.setActiveLeafByPoint(p);
                break;
            case MotionEvent.ACTION_MOVE:
                activeFrogMovingCoord = p;
                break;
            case MotionEvent.ACTION_UP:
                isActiveFrogMoving = false;
                swampMap.dropActiveFrog(p);
                swampMap.resetActiveLeaf();
                break;
            default:
                break;
        }

        invalidate();
        return true;
    }

    private SwampMap createSwampMap() {
        HashMap<Integer, LeafState> states = new HashMap<>();
        states.put(3, LeafState.GREEN_FROG);
        states.put(5, LeafState.RED_FROG);
        states.put(4, LeafState.GREEN_FROG);
        states.put(10, LeafState.GREEN_FROG);
        states.put(2, LeafState.GREEN_FROG);
        states.put(0, LeafState.GREEN_FROG);
        return new SwampMap(states);
    }

}
