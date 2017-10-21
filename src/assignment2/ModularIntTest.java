package assignment2;

import org.junit.Test;

import static org.junit.Assert.*;

public class ModularIntTest {

    @Test
    public void testInverse1() {
        int m = 5;
        assertEquals("4", new ModularInt(4, m).inverse().toString());
    }

    @Test
    public void testInverse2() {
        int m = 37;
        assertEquals("25", new ModularInt(3, m).inverse().toString());
        assertEquals("3", new ModularInt(25, m).inverse().toString());
    }

    @Test
    public void testInverse3() {
        int m = 37;
        assertEquals("22", new ModularInt(-5, m).inverse().toString());
        assertEquals("32", new ModularInt(22, m).inverse().toString());
    }

    @Test
    public void testInverse4() {
        int m = 83;
        assertEquals("21", new ModularInt(4, m).inverse().toString());
        assertEquals("4", new ModularInt(21, m).inverse().toString());
    }

    @Test
    public void testInverse5() {
        int m = 83;
        assertEquals("1", new ModularInt(1, m).inverse().toString());
    }
}