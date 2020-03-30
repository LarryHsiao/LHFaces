package com.larryhsiao.lhfaces;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.os.Handler;
import android.support.wearable.watchface.CanvasWatchFaceService;

import java.util.Calendar;

public class WatchFace extends CanvasWatchFaceService {
    private final Engine engine = new Engine();

    private final Handler main = new Handler();
    private final Runnable secondUpdate = new Runnable() {
        @Override
        public void run() {
            engine.invalidate();
            main.postDelayed(this, 1000);
        }
    };

    @Override
    public void onCreate() {
        super.onCreate();
        main.postDelayed(secondUpdate, 1000);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        main.removeCallbacks(secondUpdate);
    }

    @Override
    public Engine onCreateEngine() {
        return engine;
    }

    private class Engine extends CanvasWatchFaceService.Engine {
        private final Paint backgroundPaint = new BackgroundPaint().value();
        private final Paint secondPaint = new SecondPaint().value();
        private final Paint minutePaint = new MinutePaint().value();
        private final Paint hourPaint = new HourPaint().value();

        @Override
        public void onTimeTick() {
            super.onTimeTick();
            invalidate();
        }

        @Override
        public void onAmbientModeChanged(boolean inAmbientMode) {
            super.onAmbientModeChanged(inAmbientMode);
            invalidate();
        }

        @Override
        public void onDraw(Canvas canvas, Rect bounds) {
            canvas.drawRect(0,
                0,
                bounds.width(),
                bounds.height(),
                backgroundPaint);
            Calendar calendar = Calendar.getInstance();
            drawMinute(calendar.get(Calendar.MINUTE), canvas, bounds);
            drawHour(calendar.get(Calendar.HOUR), canvas, bounds);
            if (!isInAmbientMode()) {
                drawSecond(calendar.get(Calendar.SECOND), canvas, bounds);
            }
        }

        private void drawMinute(int minute, Canvas canvas, Rect bounds) {
            final float degrees = 360 * (minute / 60f);
            canvas.rotate(degrees, bounds.centerX(), bounds.centerY());
            canvas.drawLine(
                bounds.centerX(),
                bounds.centerY(),
                bounds.centerX(),
                bounds.centerY() - (bounds.width() * 0.5f *0.8f),
                minutePaint
            );
            canvas.rotate(-degrees, bounds.centerX(), bounds.centerY());
        }

        private void drawSecond(int second, Canvas canvas, Rect bounds) {
            final float degrees = 360 * (second / 60f);
            canvas.rotate(degrees, bounds.centerX(), bounds.centerY());
            canvas.drawLine(
                bounds.centerX(),
                bounds.centerY(),
                bounds.centerX(),
                bounds.centerY() - (bounds.width() * 0.5f * 0.85f),
                secondPaint
            );
            canvas.rotate(-degrees, bounds.centerX(), bounds.centerY());
        }

        private void drawHour(int hour, Canvas canvas, Rect bounds) {
            final float degrees = 360 * (hour / 12f);
            canvas.rotate(degrees, bounds.centerX(), bounds.centerY());
            canvas.drawLine(
                bounds.centerX(),
                bounds.centerY(),
                bounds.centerX(),
                bounds.centerY() - (bounds.width() *0.5f *0.5f),
                hourPaint
            );
            canvas.rotate(-degrees, bounds.centerX(), bounds.centerY());
        }
    }
}
