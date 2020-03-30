package com.larryhsiao.lhfaces.config;

import android.content.Context;
import android.content.SharedPreferences;
import com.silverhetch.clotho.Source;

/**
 * Source to build SharedPreference.
 */
public class SP implements Source<SharedPreferences> {
    private final Context context;

    public SP(Context context) {
        this.context = context;
    }

    @Override
    public SharedPreferences value() {
        return context.getSharedPreferences("SP_LH", Context.MODE_PRIVATE);
    }
}
