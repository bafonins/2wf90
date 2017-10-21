package assignment2;

import org.junit.Test;

import static org.junit.Assert.*;

public class ModularIntTest {

    @Test
    public void testInverse1() {
        int m = 5;
        ModularInt val = new ModularInt(4, m);

        assertEquals("4", val.inverse().toString());
    }

    @Test
    public void testInverse2() {
        int m = 37;
        ModularInt val = new ModularInt(3, m);

        assertEquals("25", val.inverse().toString());
    }

    @Test
    public void testInverse3() {
        int m = 37;
        ModularInt val = new ModularInt(-5, m);

        assertEquals("22", val.inverse().toString());
    }

    @Test
    public void testInverse4() {
        int m = 83;
        ModularInt val = new ModularInt(4, m);

        assertEquals("21", val.inverse().toString());
    }

    @Test
    public void testInverse5() {
        int m = 83;
        ModularInt val = new ModularInt(1, m);

        assertEquals("1", val.inverse().toString());
    }

}