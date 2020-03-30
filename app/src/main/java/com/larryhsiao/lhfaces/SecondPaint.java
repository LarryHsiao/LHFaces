package com.larryhsiao.lhfaces;

import android.graphics.CornerPathEffect;
import android.graphics.Paint;
import com.larryhsiao.lhfaces.config.ColorPref;
import com.silverhetch.clotho.Source;

import static android.graphics.Color.RED;

/**
 * Source to build a Paint obj for drawing second indicator.
 */
public class SecondPaint implements Source<Paint> {
    private final ColorPref colorPref;

    public SecondPaint(ColorPref colorPref) {this.colorPref = colorPref;}

    @Override
    public Paint value() {
        final Paint paint = new Paint();
        paint.setColor(colorPref.color());
        paint.setStrokeWidth(3f);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeJoin(Paint.Join.ROUND);
        paint.setStrokeCap(Paint.Cap.ROUND);
        paint.setPathEffect(new CornerPathEffect(10));
        paint.setAntiAlias(true);
        return paint;
    }
}
