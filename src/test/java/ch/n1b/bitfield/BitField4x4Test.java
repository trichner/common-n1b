package ch.n1b.bitfield;

import junit.framework.Assert;
import org.junit.Test;

/**
 * Created on 06.01.2015.
 *
 * @author Thomas
 */
public class BitField4x4Test {

    final public static short TEST = 0b0110010101010110;
    final public static short EXPECTED = 0b0110101010100110;

    @Test
    public void flipTest(){
        Bitfield4x4 field = new Bitfield4x4(TEST);
        System.out.println(field);
        field.flipHorizontally();
        System.out.println(field);
        Assert.assertEquals(EXPECTED,field);
    }
}
