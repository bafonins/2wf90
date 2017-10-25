package assignment2;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;

/**
 * This class represents a finite field Z/pZ/(f(x)) where f(x) is an irreducible polynomial and
 * p is prime.
 */
public class FField {

    private int p;
    private final Polynomial poly;
    private Polynomial[] elements;

    /**
     * Initializes a new instance of {@code FField}
     * @param p The prime number
     * @param poly The irreducible polynomial.
     */
    public FField(int p, Polynomial poly) {
        Objects.requireNonNull(poly);

        this.p = p;
        this.poly = poly;
        generateField(poly.getDegree());
    }

    /**
     * Generates a set of polynomials within this field.
     * @param md The degree of the bounding polynomial.
     */
    private void generateField(int md) {
        ArrayList<Polynomial> result = new ArrayList<>();
        for (int i = 0; i < md; i++) {
            ModularInt[] ts = new ModularInt[i + 1];
            for (int j = 0; j < ts.length; j++) {
                ts[j] = new ModularInt(0, this.p);
            }
            generateOfDegree(i, 0, 0, result, ts);
        }
        
        this.elements = result.toArray(new Polynomial[result.size()]);
    }

    /**
     * Recursively generates all polynomials of degree {@code ofDegree} `mod p`.
     */
    private void generateOfDegree(int ofDegree, int m, int idx, ArrayList<Polynomial> l, ModularInt[] ts) {

        if (idx == ofDegree + 1) {
            l.add(new Polynomial(ts, this.p));
            return;
        }

        for (int i = m; i < this.p; i++) {
            for (int j = idx; j <= ofDegree; j++) {
                ts[j] = new ModularInt(i, this.p);
            }
            generateOfDegree(ofDegree, m + 1, idx + 1, l, Arrays.copyOf(ts, ts.length));
        }
    }

    public Polynomial[][] produceAdditionTable() {
        int length = this.elements.length;
        Polynomial[][] result = new Polynomial[length][length];

        for (int i = 0; i < length; i++) {
            for (int j = 0; j < length; j++) {
                result[i][j] = this.sum(this.elements[i], this.elements[j]);
            }
        }

        return result;
    }

    public Polynomial[][] produceMultiplicationTable() {
        int length = this.elements.length;
        Polynomial[][] result = new Polynomial[length][length];

        for (int i = 0; i < length; i++) {
            for (int j = 0; j < length; j++) {
                result[i][j] = this.product(this.elements[i], this.elements[j]);
            }
        }

        return result;
    }

    public Polynomial sum(Polynomial a, Polynomial b) {
        Polynomial sum = new Polynomial(a).sum(b);
        return sum.longDivision(this.poly)[1];
    }

    public Polynomial product(Polynomial a, Polynomial b) {
        Polynomial product = new Polynomial(a).product(b);
        return product.longDivision(this.poly)[1];
    }

    /**
     * Computes a * b^1
     * @param a The first polynomial.
     * @param b The second polynomial.
     * @return a * b^1 or {@code null} if {@code b} is not invertible/zero.
     */
    public Polynomial quotent(Polynomial a, Polynomial b) {
        Polynomial inverseB = this.inverse(b);
        if (inverseB == null || inverseB.isZeroPolynomial()) {
            return null;
        }

        return this.product(a, b);
    }

    /**
     * Finds an inverse of {@code a}. See the course notes, p. 18, Algorithm 2.3.3
     * @param a The polynomial for which to get an inverse.
     * @return The inverse of {@code a} or {@code null} if it is not invertible.
     */
    public Polynomial inverse(Polynomial a) {
        // a.sum(Polynomial.initSingle(0, this.p, 0, 0)) is used reduce the polynomial a
        Polynomial gcd = Polynomial.GCD(a, this.poly);

        if (gcd.isOnePolynomial()) {
            return Polynomial.extendedGCD(a, this.poly)[0];
        } else {
            return null;
        }
    }

    public Polynomial getPoly() {
        return this.poly;
    }

    public int getP() {
        return this.p;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("{ ");
        for (int i = 0; i < this.elements.length - 1; i++) {
            sb.append(this.elements[i].toString()).append(", ");
        }
        sb.append(this.elements[this.elements.length - 1]).append(" }");
        return sb.toString();
    }
}
