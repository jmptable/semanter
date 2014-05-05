package us.semanter.app.ui.review;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;

import org.opencv.core.Point;

import us.semanter.app.ui.VisionView;
import us.semanter.app.vision.Flattener;
import us.semanter.app.vision.util.Polygon;

public class FlattenerView extends VisionView {
    private Flattener.Result result;
    private Bitmap prior;

    public FlattenerView(Context ctx, AttributeSet attrSet) {
        super(ctx, attrSet);
    }

    public void review(Flattener.Result result) {
        this.result = result;
        prior = BitmapFactory.decodeFile(result.getPrior().getPath());
    }

    @Override
    protected void render(Canvas canvas) {
        Paint p = new Paint();
        p.setColor(Color.MAGENTA);
        p.setStrokeWidth(4);

        canvas.drawBitmap(prior, 0, 0, p);

        // draw outlines of paper
        for(Polygon poly: result.getPolygons()) {
            try {
                p.setAlpha((int)(128 + result.getConfidence(poly) * 128));

                Point[] points = poly.getPoints();
                for(int i=0; i < points.length + 1; i++) {
                    Point first = points[i];
                    Point second = points[(i+1)%points.length];
                    canvas.drawLine((float)first.x, (float)first.y, (float)second.x, (float)second.y, p);
                }
            } catch(Exception e) {
                e.printStackTrace();
            }
        }
    }
}
