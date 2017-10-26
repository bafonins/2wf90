package assignment2;

import org.junit.Test;


import static org.junit.Assert.*;

public class FFieldTest {

    @Test
    public void testFiniteFieldGeneration1() {
        // Z/2Z/(1X^2+1X^1+1)
        FField field = new FField(2, Polynomial.init(2, 2, 1, 1, 1));

        assertEquals("{ 0, 1, 1X^1, 1X^1+1 }", field.toString());
    }

    @Test
    public void testFiniteFieldGeneration2() {
        // Z/3Z/(1X^2+1X^1+1)
        FField field = new FField(3, Polynomial.init(2, 3, 1, 1, 1));

        assertEquals("{ 0, 1, 2, 1X^1, 2X^1, 1X^1+1, 2X^1+1, 1X^1+2, 2X^1+2 }", field.toString());
    }

    @Test
    public void testAdditionTable() {
        FField field = new FField(2, Polynomial.init(2, 2, 1, 1, 1));

        String[][] expected = {
                {"0", "1", "1X^1", "1X^1+1"},
                {"1", "0", "1X^1+1", "1X^1"},
                {"1X^1", "1X^1+1", "0", "1"},
                {"1X^1+1", "1X^1", "1", "0"}
        };
        Polynomial[][] res = field.produceAdditionTable();

        for (int i = 0; i < res.length; i++) {
            for (int j = 0; j < res[i].length; j++) {
                assertEquals(expected[i][j], res[i][j].toString());
            }
        }

    }

    @Test
    public void testMultiplicationTable() {
        FField field = new FField(2, Polynomial.init(2, 2, 1, 1, 1));

        String[][] expected = {
                {"0", "0", "0", "0"},
                {"0", "1", "1X^1", "1X^1+1"},
                {"0", "1X^1", "1X^1+1", "1"},
                {"0", "1X^1+1", "1", "1X^1"}
        };
        Polynomial[][] res = field.produceMultiplicationTable();

        for (int i = 0; i < expected.length; i++) {
            for (int j = 0; j < expected[i].length; j++) {
                assertEquals(expected[i][j], res[i][j].toString());
            }
        }
    }

    @Test
    public void testIrreducible() {
        FField field = new FField(7, Polynomial.init(3, 7, 1, 1, 1, 1));
        Polynomial poly = Polynomial.init(2, 7, 3, 0, 1);
        assertFalse(field.isIrreducible(poly));
    }
}