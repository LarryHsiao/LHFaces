package com.larryhsiao.lhfaces.config;

import com.larryhsiao.lhfaces.utility.ParsedInt;
import com.silverhetch.clotho.source.ConstSource;
import com.silverhetch.clotho.storage.Ceres;

import static android.graphics.Color.WHITE;

/**
 * Source to build the color string of second strored in storage.
 */
public class SecondColor implements ColorPref {
    private static final String KEY_SECOND_COLOR = "KEY_SECOND_COLOR";
    private final Ceres ceres;

    public SecondColor(Ceres ceres) {
        this.ceres = ceres;
    }

    @Override
    public void newColor(int color) {
        ceres.store(KEY_SECOND_COLOR, color + "");
    }

    @Override
    public int color() {
        return new ParsedInt(
            new ConstSource<String>(ceres.get(KEY_SECOND_COLOR)),
            WHITE
        ).value();
    }
}

