package akirakozov.ru.smartjumper.drawing;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import java.util.HashMap;
import java.util.Map;

import akirakozov.ru.smartjumper.map.LeafInfo;
import akirakozov.ru.smartjumper.map.LeafState;
import akirakozov.ru.smartjumper.map.Point;
import akirakozov.ru.smartjumper.map.SwampMap;

/**
 * Created by akirakozov on 26.06.16.
 */
public class SwampEditorView extends View {
    private final SwampMap swampMap;

    public SwampEditorView(Context context, AttributeSet attributes) {
        super(context, attributes);
        this.swampMap = new SwampMap(new HashMap<Integer, LeafState>());
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
            case MotionEvent.ACTION_UP:
                int leaf = swampMap.findLeafByPoint(p);
                LeafState curState = swampMap.getLeafs().get(leaf).state;
                swampMap.changeLeafState(leaf, nextState(curState));
                break;
            default:
                break;
        }

        invalidate();
        return true;
    }

    public Map<Integer, LeafState> getStates() {
        return swampMap.getLeafStates();
    }

    private LeafState nextState(LeafState curState) {
        if (curState == LeafState.EMPTY) {
                return LeafState.GREEN_FROG;
        } else if (curState == LeafState.GREEN_FROG) {
            return LeafState.RED_FROG;
        } else {
            return LeafState.EMPTY;
        }
    }
}
