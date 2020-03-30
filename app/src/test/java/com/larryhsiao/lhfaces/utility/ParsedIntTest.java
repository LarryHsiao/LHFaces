package com.larryhsiao.lhfaces.utility;

import com.silverhetch.clotho.source.ConstSource;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Unit-test for the class {@link ParsedInt}
 */
public class ParsedIntTest {

    /**
     * Normal case
     */
    @Test
    public void normalCase() {
        assertEquals(
            123,
            new ParsedInt(
                new ConstSource<>("123"),
                10000
            ).value().intValue()
        );
    }

    /**
     * Not number
     */
    @Test
    public void notNumber() {
        assertEquals(
            10000,
            new ParsedInt(
                new ConstSource<>("123A"),
                10000
            ).value().intValue()
        );
    }
    /**
     * Empty returns default
     */
    @Test
    public void empty() {
        assertEquals(
            10000,
            new ParsedInt(
                new ConstSource<>(""),
                10000
            ).value().intValue()
        );
    }

}