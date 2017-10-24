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

}