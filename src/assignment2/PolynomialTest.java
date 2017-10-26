package assignment2;

import org.junit.Test;

import static org.junit.Assert.*;

public class PolynomialTest {

    @Test
    public void testSumWithZero() {
        int m = 7;
        // 1X^4+1X^1+3
        Polynomial poly = new Polynomial(new ModularInt[] {
                new ModularInt(3, m),
                new ModularInt(1, m),
                new ModularInt(0, m),
                new ModularInt(0, m),
                new ModularInt(1, m)}, m);
        Polynomial polyZero = new Polynomial(new ModularInt[] {new ModularInt(0, m)}, m);

        String beforeSum = poly.toString();
        poly.sum(polyZero);
        assertEquals(beforeSum, poly.toString());
    }

    @Test
    public void testSumWithZeroReversed() {
        int m = 7;
        // 1X^4+1X^1+3
        Polynomial poly = new Polynomial(new ModularInt[] {
                new ModularInt(3, m),
                new ModularInt(1, m),
                new ModularInt(0, m),
                new ModularInt(0, m),
                new ModularInt(1, m)}, m);
        Polynomial polyZero = new Polynomial(new ModularInt[] {new ModularInt(0, m)}, m);

        String beforeSum = poly.toString();
        polyZero.sum(poly);
        assertEquals(beforeSum, poly.toString());
    }

    @Test
    public void testSumWithOne() {
        int m = 7;
        // 1X^4+1X^1+3
        Polynomial poly = getPolynomial(4, m, 3, 1, 0, 0, 1);
        Polynomial polyOne = getPolynomial(0, m, 1);

        poly.sum(polyOne);
        assertEquals("1X^4+1X^1+4", poly.toString());
    }

    @Test
    public void testSumWithModulus() {
        int m = 37;
        // 1X^6+36X^4+36X^3-15X^2+13X-30
        Polynomial poly = getPolynomial(6, m, -30, 13, -15, 36, 36, 0, 1);
        Polynomial poly2 = getPolynomial(0, m, m);
        String initial = poly.toString();

        poly.sum(poly2);

        assertEquals(initial, poly.toString());
    }

    @Test
    public void testSumWithModulus2() {
        int m = 37;
        // 7X^4+6X^3+5X^2+4X^1+3
        Polynomial poly = getPolynomial(4, m, 3, 4, 5, 6, 7);
        Polynomial poly2 = getPolynomial(4, m, m, m, m, m, m);
        String initial = poly.toString();

        poly.sum(poly2);

        assertEquals(initial, poly.toString());
    }

    @Test
    public void testSum1() {
        int m = 10;
        // 10X^4+15X^3+12X^2+1X^1+3
        Polynomial poly = getPolynomial(4, m, 3, 1, 12, 15, 10);
        //                   1X^1
        Polynomial polyOne = getPolynomial(1, m, 0, 1);

        poly.sum(polyOne);

        assertEquals("5X^3+2X^2+2X^1+3", poly.toString());
    }

    @Test
    public void testSum2() {
        int m = 10;
        // 10X^4+15X^3+12X^2+1X^1+3
        Polynomial poly = getPolynomial(4, m, 3, 1, 12, 15, 10);
        //        4X^3+11X^2+11X^1+20
        Polynomial poly2 = getPolynomial(3, m, 20, 11, 11, 4);

        poly.sum(poly2);

        assertEquals("9X^3+3X^2+2X^1+3", poly.toString());

    }

    @Test
    public void testSum3() {
        int m = 5;
        // 2X^4+4X^3-2X^2+2X^1-6
        Polynomial poly = getPolynomial(4, m, -6, 2, -2, 4, 2);
        //      2X^3-2X^2+6X^1+3
        Polynomial poly2 = getPolynomial(3, m, 3, 6, -2, 2);

        poly.sum(poly2);

        assertEquals("2X^4+1X^3+1X^2+3X^1+2", poly.toString());
    }

    @Test
    public void testSum4() {
        int m = 37;
        // 1X^6+36X^4+36X^3-15X^2+13X-30
        Polynomial poly = getPolynomial(6, m, -30, 13, -15, 36, 36, 0, 1);
        // -1X^6-36X^4-36X^3+15X^2-13X+30
        Polynomial poly2 = getPolynomial(6, m, 30, -13, 15, -36, -36, 0, -1);

        poly.sum(poly2);

        assertEquals("0", poly.toString());
    }


    @Test
    public void testScalarMultipleWithZero() {
        int m = 5;
        int sm = 0;

        // 1X^4+1X^3+1X^2+1X^1+1
        Polynomial poly = getPolynomial(4, m, 1, 1, 1, 1, 1);

        poly.scalarMultiple(sm);

        assertEquals("0", poly.toString());
    }

    @Test
    public void testScalarMultipleWithOne() {
        int m = 5;
        int sm = 1;

        // 1X^4+1X^3+1X^2+1X^1+1
        Polynomial poly = getPolynomial(4, m, 1, 1, 1, 1, 1);
        String initial = poly.toString();

        poly.scalarMultiple(sm);

        assertEquals(initial, poly.toString());
    }

    @Test
    public void testScalarMultipleWithModulus() {
        int m = 5;
        int sm = 5;

        // 2X^4+2X^3+2X^2+2X^1+2
        Polynomial poly = getPolynomial(4, m, 2, 2, 2, 2, 2);

        poly.scalarMultiple(sm);

        assertEquals("0", poly.toString());
    }

    @Test
    public void testScalar1() {
        int m = 5;
        int sm = 3;

        // 2X^4+3X^3+4X^2+2X^1+2
        Polynomial poly = getPolynomial(4, m, 2, 2, 4, 3, 2);

        poly.scalarMultiple(sm);

        assertEquals("1X^4+4X^3+2X^2+1X^1+1", poly.toString());
    }

    @Test
    public void testScalar2() {
        int m = 5;
        int sm = -3;

        // 2X^4+3X^3+4X^2+2X^1+2
        Polynomial poly = getPolynomial(4, m, 2, 2, 4, 3, 2);

        poly.scalarMultiple(sm);

        assertEquals("4X^4+1X^3+3X^2+4X^1+4", poly.toString());
    }

    @Test
    public void testDifferenceWithZero() {
        int m = 5;

        // 4X^6+2X^5+4X^3+X^2+X^1+4
        Polynomial poly = getPolynomial(6, m, 4, 1, 1, 4, 0, 2, 4);
        Polynomial poly2 = getPolynomial(0, m, 0);
        String initial = poly.toString();

        poly.difference(poly2);

        assertEquals(initial, poly.toString());
    }

    @Test
    public void testDifferenceWithSelf() {
        int m = 5;

        // 4X^6+2X^5+4X^3+X^2+X^1+4
        Polynomial poly = getPolynomial(6, m, 5, 1, 1, 4, 0, 2, 4);
        // 4X^6+2X^5+4X^3+X^2+X^1+4
        Polynomial poly2 = getPolynomial(6, m, 5, 1, 1, 4, 0, 2, 4);

        poly.difference(poly2);

        assertEquals("0", poly.toString());
    }

    @Test
    public void testDifference1() {
        int m = 5;
        // 4X^6+2X^5+4X^3+X^2+X^1+4
        Polynomial poly = getPolynomial(6, m, 5, 1, 1, 4, 0, 2, 4);
        // 12X^6+2X^5+4X^3+X^2+X^1+4
        Polynomial poly2 = getPolynomial(6, m, 5, 1, 1, 4, 0, 2, 6);

        poly.difference(poly2);

        assertEquals("3X^6", poly.toString());
    }

    @Test
    public void testDifference2() {
        int m = 37;
        // 38X^6+38X^5+38X^3+38X^3+38X^2+38X^1+38
        Polynomial poly = getPolynomial(6, m, 38, 38, 38, 38, 38, 38, 38);
        // 39X^6+39X^5+39X^3+39X^2+39X^1+39
        Polynomial poly2 = getPolynomial(6, m, 39, 39, 39, 39, 39, 39, 39);

        poly.difference(poly2);

        assertEquals("36X^6+36X^5+36X^4+36X^3+36X^2+36X^1+36", poly.toString());
    }

    @Test
    public void testProductWithZero() {
        int m = 37;

        // 38X^6+38X^5+38X^3+38X^3+38X^2+38X^1+38
        Polynomial poly = getPolynomial(6, m, 38, 38, 38, 38, 38, 38, 38);
        Polynomial zero = getPolynomial(0, m, 0);

        poly.product(zero);

        assertEquals("0", poly.toString());
    }

    @Test
    public void testProductWithOne() {
        int m = 37;

        // 3X^2+3X^1+3
        Polynomial poly = getPolynomial(2, m, 3, 3, 3);
        Polynomial one = getPolynomial(0, m, 1);
        String initial = poly.toString();

        poly.product(one);

        assertEquals(initial, poly.toString());
    }

    @Test
    public void testProductWithModulus() {
        int m = 37;

        // 3X^2+3X^1+3
        Polynomial poly = getPolynomial(2, m, 3, 3, 3);
        Polynomial modulus = getPolynomial(0, m, 37);

        poly.product(modulus);

        assertEquals("0", poly.toString());
    }

    @Test
    public void testProductWithModulus2() {
        int m = 37;

        // 3X^2+3X^1+3
        Polynomial poly = getPolynomial(2, m, 3, 3, 3);
        // 37X^2+37X^1+37
        Polynomial poly2 = getPolynomial(2, m, 37, 37, 37);

        poly.product(poly2);

        assertEquals("0", poly.toString());
    }

    @Test
    public void testProduct1() {
        int m = 11;

        // 2X^1+2
        Polynomial poly = getPolynomial(1, m, 2, 2);
        // 2X^1+2
        Polynomial poly2 = getPolynomial(1, m, 2, 2);

        poly.product(poly2);

        assertEquals("4X^2+8X^1+4", poly.toString());
    }

    @Test
    public void testProduct2() {
        int m = 11;

        // 4X^3+3X^2+2X^1+2
        Polynomial poly = getPolynomial(3, m, 2, 2, 3, 4);
        // 2X^1+2
        Polynomial poly2 = getPolynomial(1, m, 2, 2);

        poly.product(poly2);

        assertEquals("8X^4+3X^3+10X^2+8X^1+4", poly.toString());
    }

    @Test
    public void testProduct3() {
        int m = 11;

        // 6X^4-3X^3+1X^2-15X^1-4
        Polynomial poly = getPolynomial(4, m, -4, -15, 1, -3, 6);
        // 6X^3+3X^2-2X^1+1
        Polynomial poly2 = getPolynomial(3, m, 1, -2, 3, 6);

        poly.product(poly2);

        assertEquals("3X^7+7X^5+2X^4+3X^3+8X^2+4X^1+7", poly.toString());
    }

    @Test
    public void testLongDivisionWithOne() {
        int m = 5;

        // 2X^3+2X^2+2X^1+2
        Polynomial poly = getPolynomial(3, m, 2, 2, 2, 2);
        Polynomial one = getPolynomial(0, m, 1);

        Polynomial[] res = poly.longDivision(one);

        assertEquals("2X^3+2X^2+2X^1+2", res[0].toString());
        assertEquals("0", res[1].toString());
    }

    @Test
    public void testLongDivisionWithTwo() {
        int m = 5;

        // 2X^3+2X^2+2X^1+2
        Polynomial poly = getPolynomial(3, m, 2, 2, 2, 2);
        Polynomial two = getPolynomial(0, m, 2);

        Polynomial[] res = poly.longDivision(two);

        assertEquals("1X^3+1X^2+1X^1+1", res[0].toString());
        assertEquals("0", res[1].toString());
    }

    @Test
    public void testLongDivisionWithSelf() {
        int m = 5;

        // 1X^3+1X^2+1X^1+1
        Polynomial poly = getPolynomial(3, m, 1, 1, 1, 1);
        // 1X^3+1X^2+1X^1+1
        Polynomial poly2 = getPolynomial(3, m, 1, 1, 1, 1);

        Polynomial[] res = poly.longDivision(poly2);

        assertEquals("1", res[0].toString());
        assertEquals("0", res[1].toString());
    }

    @Test
    public void testLongDivision1() {
        int m = 5;

        // 1X^3+1X^2+1X^1+1
        Polynomial poly = getPolynomial(3, m, 1, 1, 1, 1);
        // 1X^1
        Polynomial poly2 = getPolynomial(1, m, 0, 1);

        Polynomial[] res = poly.longDivision(poly2);

        assertEquals("1X^2+1X^1+1", res[0].toString());
        assertEquals("1", res[1].toString());
    }

    @Test
    public void testLongDivision2() {
        int m = 5;

        // 3X^3+2X^2+1X^1+1
        Polynomial poly = getPolynomial(3, m, 1, 1, 2, 3);
        // 1X^2+1X^1
        Polynomial poly2 = getPolynomial(2, m, 0, 1, 1);

        Polynomial[] res = poly.longDivision(poly2);

        assertEquals("3X^1+4", res[0].toString());
        assertEquals("2X^1+1", res[1].toString());
    }

    @Test
    public void testLongDivision3() {
        int m = 5;

        // 3X^3+3X^2
        Polynomial poly = getPolynomial(3, m, 0, 0, 3, 3);
        // 4X^3+4X^2+1X^1
        Polynomial poly2 = getPolynomial(3, m, 0, 1, 4, 4);

        Polynomial[] res = poly.longDivision(poly2);

        assertEquals("2", res[0].toString());
        assertEquals("3X^1", res[1].toString());
    }

    @Test
    public void testLongDivision4() {
        int m = 5;

        // 1X^1+1
        Polynomial poly = getPolynomial(1, m, 1, 1);
        // 4
        Polynomial poly2 = getPolynomial(0, m, 4);

        Polynomial[] res = poly.longDivision(poly2);

        assertEquals("4X^1+4", res[0].toString());
        assertEquals("0", res[1].toString());
    }

    @Test
    public void testGCDSelf() {
        int m = 5;

        // 1X^2
        Polynomial poly = getPolynomial(1, m, 0, 1);
        Polynomial poly2 = getPolynomial(1, m, 0, 1);
        String initial = poly.toString();

        Polynomial gcd = Polynomial.GCD(poly, poly2);

        assertEquals(initial, gcd.toString());
    }

    @Test
    public void testGCDWithOne() {
        int m = 5;

        // 1X^2
        Polynomial poly = getPolynomial(1, m, 0, 1);
        Polynomial poly2 = getPolynomial(0, m, 1);

        Polynomial gcd = Polynomial.GCD(poly, poly2);

        assertEquals("1", gcd.toString());
    }

    @Test
    public void testGCD1() {
        int m = 5;

        // 3X^3
        Polynomial poly = getPolynomial(3, m, 0, 0, 0, 3);
        Polynomial poly2 = getPolynomial(3, m, 0, 0, 0, 1);

        Polynomial gcd = Polynomial.GCD(poly, poly2);

        assertEquals("1X^3", gcd.toString());
    }

    @Test
    public void testGCD2() {
        int m = 5;

        // 6X^3+1X^1+1
        Polynomial poly = getPolynomial(3, m, 1, 1, 0, 6);
        // 1X^3+1X^1+1
        Polynomial poly2 = getPolynomial(3, m, 1, 1, 0, 1);

        Polynomial gcd = Polynomial.GCD(poly, poly2);

        assertEquals("1X^3+1X^1+1", gcd.toString());
    }

    @Test
    public void testGCD3() {
        int m = 5;

        // 1X^3+1X^1+1
        Polynomial poly = getPolynomial(3, m, 1, 1, 0, 1);
        // 1X^3
        Polynomial poly2 = getPolynomial(3, m, 0, 0, 0, 1);

        Polynomial gcd = Polynomial.GCD(poly, poly2);

        assertEquals("4", gcd.toString());
    }

    @Test
    public void testGCD4() {
        int m = 5;

        // 1X^5+1
        Polynomial poly = getPolynomial(5, m, 1, 0, 0, 0, 0, 1);
        // 1X^3+1
        Polynomial poly2 = getPolynomial(3, m, 1, 0, 0, 1);

        Polynomial gcd = Polynomial.GCD(poly, poly2);

        assertEquals("1X^1+1", gcd.toString());
    }

    @Test
        public void testGCD4Reversed() {
        int m = 5;

        // 1X^3+1
        Polynomial poly = getPolynomial(3, m, 1, 0, 0, 1);
        // 1X^5+1
        Polynomial poly2 = getPolynomial(5, m, 1, 0, 0, 0, 0, 1);

        Polynomial gcd = Polynomial.GCD(poly, poly2);

        assertEquals("1X^1+1", gcd.toString());
    }

    @Test
    public void testGCD5() {
        int m = 5;

        // 6X^5+11
        Polynomial poly = getPolynomial(5, m, 11, 0, 0, 0, 0, 6);
        // 11X^3+1
        Polynomial poly2 = getPolynomial(3, m, 1, 0, 0, 11);

        Polynomial gcd = Polynomial.GCD(poly, poly2);

        assertEquals("1X^1+1", gcd.toString());
    }

    @Test
    public void testExtendedGCDWithSelf() {
        int m = 5;

        // 1X^4
        Polynomial poly = getPolynomial(4, m, 0, 0, 0, 0, 1);
        Polynomial poly2 = getPolynomial(4, m, 0, 0, 0, 0, 1);

        Polynomial[] res = Polynomial.extendedGCD(poly, poly2);

        assertEquals("0", res[0].toString());
        assertEquals("1", res[1].toString());
    }

    @Test
    public void testExtendedGCDWithOne() {
        int m = 5;

        // 1X^4
        Polynomial poly = getPolynomial(4, m, 0, 0, 0, 0, 1);
        Polynomial one = getPolynomial(0, m, 1);

        Polynomial[] res = Polynomial.extendedGCD(poly, one);

        assertEquals("0", res[0].toString());
        assertEquals("1", res[1].toString());
    }

    @Test
    public void testExtendedGCD1() {
        int m = 5;

        // 1X^2+1X^1
        Polynomial poly = getPolynomial(2, m, 0, 1, 1);
        // 1X^2
        Polynomial poly2 = getPolynomial(2, m, 0, 0, 1);

        Polynomial[] res = Polynomial.extendedGCD(poly, poly2);

        assertEquals("1", res[0].toString());
        assertEquals("4", res[1].toString());
    }

    @Test
    public void testExtendedGCD2() {
        int m = 5;

        // 1X^5+1X^3
        Polynomial poly = getPolynomial(5, m, 0, 0, 0, 1, 0, 1);
        // 1X^2+1X^1
        Polynomial poly2 = getPolynomial(2, m, 0, 1, 1);

        Polynomial[] res = Polynomial.extendedGCD(poly, poly2);

        Polynomial gcd = Polynomial.GCD(poly, poly2);
        Polynomial left = poly.product(res[0]).sum(poly2.product(res[1])); // x*a + y*b

        assertEquals(gcd.toString(), left.toString());
    }

    @Test
    public void testExtendedGCD3() {
        int modulo = 5;
        Polynomial poly = getPolynomial(5, modulo, 0, 4, 0, 0, 0, 1);
        Polynomial poly2 = getPolynomial(3, modulo, 3, 0, 0, 1);

        Polynomial[] res = Polynomial.extendedGCD(poly, poly2);
        assertEquals("2X^1+1", res[0].toString());
        assertEquals("3X^3+4X^2+1", res[1].toString());
    }

    @Test
    public void testExtendedGCD4() {
        int modulo = 2;
        Polynomial poly = getPolynomial(6, modulo, 1, 1, 0, 0, 1, 0, 1);
        Polynomial poly2 = getPolynomial(8, modulo, 1, 1, 0, 1, 1, 0, 0, 0, 1);

        Polynomial[] res = Polynomial.extendedGCD(poly, poly2);

        assertEquals("1X^7+1X^6+1X^3+1X^1", res[0].toString());
        assertEquals("1X^5+1X^4+1X^3+1X^2+1", res[1].toString());
    }

    @Test
    public void testCongruent1() {
        int m = 5;

        // 1X^3
        Polynomial poly = getPolynomial(3, m, 0, 0, 0, 1);
        // 1X^2
        Polynomial poly2 = getPolynomial(2, m, 0, 0, 1);
        // 1X^1
        Polynomial poly3 = getPolynomial(1, m, 0, 1);

        assertTrue(Polynomial.congruent(poly, poly2, poly3));
    }

    @Test
    public void testCongruent2() {
        int m = 5;

        // 1X^3
        Polynomial poly = getPolynomial(3, m, 0, 0, 0, 1);
        // 1X^2
        Polynomial poly2 = getPolynomial(2, m, 0, 0, 1);
        // 1X^1
        Polynomial poly3 = getPolynomial(1, m, 1, 1);

        assertFalse(Polynomial.congruent(poly, poly2, poly3));
    }

    /**
     * A helper method. Constructs a polynomial with given coefficients and modulus.
     * @param degree The degree of the polynomial.
     * @param modulus The modulus.
     * @param values The list of coefficients of the polynomial.
     * @return A new polynomial with given characteristics.
     */
    private Polynomial getPolynomial(int degree, int modulus, Integer... values) {
        ModularInt[] terms = new ModularInt[degree + 1];

        for (int i = 0; i < values.length; i++) {
            terms[i] = new ModularInt(values[i], modulus);
        }

        return new Polynomial(terms, modulus);
    }

}