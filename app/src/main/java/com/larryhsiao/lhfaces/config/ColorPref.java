package com.larryhsiao.lhfaces.config;

/**
 * Preference of color
 */
public interface ColorPref {
    /**
     * Make it new color
     */
    void newColor(int color);

    /**
     * Current color
     * @return
     */
    int color();
}
