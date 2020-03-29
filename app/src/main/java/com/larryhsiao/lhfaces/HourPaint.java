package com.larryhsiao.lhfaces;

import android.graphics.Paint;
import com.silverhetch.clotho.Source;

import static android.graphics.Color.WHITE;

/**
 * Source to build a Paint obj for drawing second indicator.
 */
public class HourPaint implements Source<Paint> {
    @Override
    public Paint value() {
        final Paint paint = new Paint();
        paint.setColor(WHITE);
        paint.setStrokeWidth(10f);
        paint.setAntiAlias(true);
        return paint;
    }
}
