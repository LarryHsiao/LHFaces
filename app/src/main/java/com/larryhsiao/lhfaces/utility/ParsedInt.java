package com.larryhsiao.lhfaces.utility;

import com.silverhetch.clotho.Source;

/**
 * Source to build a color stored in Ceres.
 */
public class ParsedInt implements Source<Integer> {
    private final Source<String> storeColor;
    private final int defaultInt;

    public ParsedInt(Source<String> storeColor, int defaultInt) {
        this.storeColor = storeColor;
        this.defaultInt = defaultInt;
    }

    @Override
    public Integer value() {
        try {
            String stored = storeColor.value();
            if (stored == null || stored.isEmpty()) {
                return defaultInt;
            }
            return Integer.parseInt(stored);
        } catch (Exception e) {
            return defaultInt;
        }
    }
}
