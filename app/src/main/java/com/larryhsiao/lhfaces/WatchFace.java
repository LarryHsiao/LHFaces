package com.larryhsiao.lhfaces;

import android.content.SharedPreferences;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.os.Handler;
import android.support.wearable.watchface.CanvasWatchFaceService;
import android.view.SurfaceHolder;
import com.larryhsiao.lhfaces.config.SP;
import com.larryhsiao.lhfaces.config.SecondColor;
import com.silverhetch.aura.storage.SPCeres;

import java.util.Calendar;

public class WatchFace extends CanvasWatchFaceService {
    private final Engine engine = new Engine();

    @Override
    public Engine onCreateEngine() {
        return engine;
    }

    private class Engine extends CanvasWatchFaceService.Engine implements
        SharedPreferences.OnSharedPreferenceChangeListener {
        private final Handler main = new Handler();
        private SharedPreferences pref;
        private final Paint backgroundPaint = new BackgroundPaint().value();
        private Paint secondPaint;
        private final Paint minutePaint = new MinutePaint().value();
        private final Paint hourPaint = new HourPaint().value();
        private final Runnable secondUpdate = new Runnable() {
            @Override
            public void run() {
                engine.invalidate();
                main.postDelayed(this, 1000);
            }
        };

        @Override
        public void onCreate(SurfaceHolder holder) {
            super.onCreate(holder);
            main.postDelayed(secondUpdate, 1000);
            pref = new SP(getApplicationContext()).value();
            pref.registerOnSharedPreferenceChangeListener(this);
            secondPaint = new SecondPaint(
                new SecondColor(
                    new SPCeres(pref)
                )
            ).value();
        }

        @Override
        public void onDestroy() {
            super.onDestroy();
            main.removeCallbacks(secondUpdate);
            pref.unregisterOnSharedPreferenceChangeListener(this);
        }

        @Override
        public void onSharedPreferenceChanged(
            SharedPreferences sharedPreferences, String key) {
            initPaints();
        }

        private void initPaints() {
            secondPaint = new SecondPaint(
                new SecondColor(
                    new SPCeres(pref)
                )
            ).value();
        }

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
                bounds.centerY() - (bounds.width() * 0.5f * 0.8f),
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
                bounds.centerY() - (bounds.width() * 0.5f * 0.5f),
                hourPaint
            );
            canvas.rotate(-degrees, bounds.centerX(), bounds.centerY());
        }
    }
}
