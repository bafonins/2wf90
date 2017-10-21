package assignment2;

import java.util.Arrays;
import java.util.Objects;

/**
 * This class represents a polynomial.
 */
public class Polynomial {

    /**
     * Represents this polynomial.
     * For example, if terms = [1, 0, 0, 3], then the polynomial has the following form:
     * 3X^3 + 1. So, terms[0] contains the constant factor, and each terms[i], for
     * 0 < i < terms.length holds a coefficient for the term. If terms[i] = 0, then 0 * X^i.
     * Note, {@code terms.length} must be at least 1 to represent a constant polynomial.
     */
    private ModularInt[] terms;
    private int m;

    /**
     * Creates a new instance of {@code Polynomial}.
     * @param terms The terms of the polynomial.
     * @param modulo The modulo.
     */
    public Polynomial(ModularInt[] terms, int modulo) {
        Objects.requireNonNull(terms);

        this.m = modulo;
        this.terms = terms;
        this.reduce();
    }

    /**
     * Creates a copy from existing instance of {@code Polynomial}.
     * @param poly The polynomial to be as a reference.
     */
    public Polynomial(Polynomial poly) {
        this.m = poly.m;
        this.terms = Arrays.copyOf(poly.terms, poly.terms.length);
        this.reduce();
    }

    /**
     * Multiplies each term with {@code s} with every coefficient being reduced `mod` m.
     * @param s The multiple.
     * @return The polynomial after multiplying each term with {@code s}.
     */
    public Polynomial scalarMultiple(int s) {
        for (int i = 0; i < this.terms.length; i++) {
            ModularInt val = this.terms[i];
            this.terms[i] = val.set(val.getPos() * s);
        }

        return this;
    }

    /**
     * Adds two ({@code this} and {@code b}) polynomials and reduces the result `mod` m.
     * @param b The second polynomial to sum with.
     * @return The sum of two polynomials.
     */
    public Polynomial sum(Polynomial b) {
        int n_max = Math.max(b.getDegree(), this.getDegree());

        this.extend(n_max + 1);
        b.extend(n_max + 1);

        for (int i = 0; i < n_max + 1; i++) {
            this.terms[i].add(b.terms[i]);
        }

        return this;
    }

    /**
     * Subtracts {@code b} from {@code this} and reduces the result `mod` m.
     * @param b The second polynomial to subtract from {@code this}.
     * @return The difference of two polynomials.
     */
    public Polynomial difference(Polynomial b) {
        int n_max = Math.max(b.getDegree(), this.getDegree());

        this.extend(n_max + 1);
        b.extend(n_max + 1);

        for (int i = 0; i < n_max + 1; i++) {
            this.terms[i].subtract(b.terms[i]);
        }

        return this;
    }

    /**
     * Multiplies {@code b} an {@code this} and reduces the result `mod` m.
     * @param b The second polynomial to subtract from {@code this}.
     * @return The difference of two polynomials.
     */
    public Polynomial product(Polynomial b) {
        int n_max = Math.max(b.getDegree(), this.getDegree());

        this.extend(n_max + 1);
        b.extend(n_max + 1);

        ModularInt[] result = new ModularInt[(n_max + 1) * 2];
        for (int i = 0; i < result.length; i++) { result[i] = new ModularInt(0, this.m); }

        for (int i = 0; i < n_max + 1; i++) {
            for (int j = 0; j < n_max + 1; j++) {
                result[i + j].add(new ModularInt(this.terms[i]).multiply(b.terms[j]));
            }
        }

        this.terms = result;

        return this;
    }

    /**
     * Divides {@code this} with {@code b} using the long division method. See p. 5 in
     * the course notes on ffields (Algorithm 1.2.6).
     * @param b The polynomial to divide with, should not be zero.
     * @return The result of long division. An array, where arr[0] = quotient, and arr[1] = remainder.
     */
    public Polynomial[] longDivision(Polynomial b) {
        if (b.isZeroPolynomial()) {
            throw new RuntimeException("Cannot divite with the zero polynomial");
        }

        Polynomial quotient = Polynomial.init(0, this.m, 0);
        Polynomial remainder = new Polynomial(this);

        while (remainder.getDegree() >= b.getDegree() && !remainder.isZeroPolynomial()) {
            ModularInt lcR = new ModularInt(remainder.terms[remainder.getDegree()]);
            ModularInt lcB = new ModularInt(b.terms[b.getDegree()]);
            int degreeDiff = remainder.getDegree() - b.getDegree();
            ModularInt div = lcR.divide(lcB);

            Polynomial poly = Polynomial.initSingle(
                    degreeDiff,
                    this.m,
                    degreeDiff,
                    div.getPos());

            quotient.sum(poly);
            remainder.difference(poly.product(b));
        }

        return new Polynomial[] { quotient, remainder };
    }

    /**
     * Gets degree of the polynomial.
     * @return Degree of the polynomial.
     */
    public int getDegree() {
        int deg = this.terms.length - 1;
        while(deg > 0 && this.terms[deg].getPos() == 0) { deg--; }

        return deg;
    }


    @Override
    public String toString() {
        if (this.getDegree() == 0) { return this.terms[0].toString(); }

        StringBuilder sb = new StringBuilder("");

        if (this.terms[this.getDegree()].getPos() != 0) {
            int last = this.getDegree();
            sb.append(this.terms[last].getPos()).append("X^").append(last);
        }

        for (int i = this.getDegree() - 1; i > 0; i--) {
            ModularInt coef = this.terms[i];
            if (coef.getPos() != 0) {
                sb.append("+").append(coef.getPos()).append("X^").append(i);
            }
        }

        ModularInt constant = this.terms[0];
        if (constant.getPos() != 0) {
            sb.append("+").append(constant.getPos());
        }
        
        return sb.toString();
    }

    /**
     * Extends the polynomial to {@code length}.
     * @param length The new length (degree + 1) of the polynomial.
     */
    public void extend(int length) {
        if (this.terms.length == length) { return; }

        int previousLength = this.terms.length;
        this.terms = Arrays.copyOf(this.terms, length);

        for (int i = previousLength; i < length; i++) {
            this.terms[i] = new ModularInt(0, this.m);
        }
    }

    /**
     * Reduce each coefficient `mod` m.
     */
    public void reduce() {
        for (int i = 0; i < this.terms.length; i++) {
            this.terms[i].set(this.terms[i].getPos());
        }
    }

    /**
     * Determines whether this is the zero polynomial.
     * @return {@code true} if this polynomial is the zero polynomial, {@code false} otherwise.
     */
    public boolean isZeroPolynomial() {
        return this.getDegree() == 0 &&
                this.terms[0].getPos() == 0;
    }

    /**
     * Convenient method to create new polynomials from a list of coefficients.
     * @param degree The degree of the polynomial.
     * @param modulus The modulus.
     * @param coefficients The list of coefficients.
     * @return A new polynomial.
     */
    public static Polynomial init(int degree, int modulus, Integer... coefficients) {
        ModularInt[] terms = new ModularInt[degree + 1];

        for (int i = 0; i < coefficients.length; i++) {
            terms[i] = new ModularInt(coefficients[i], modulus);
        }

        return new Polynomial(terms, modulus);
    }

    /**
     * Convenient method to create new polynomials with a single coefficient.
     * @param degree The degree of the polynomial.
     * @param modulus The modulus.
     * @param idx The index of the coefficient.
     * @param coeff The coefficient.
     * @return A new polynomial.
     */
    public static Polynomial initSingle(int degree, int modulus, int idx, int coeff) {
        ModularInt[] terms = new ModularInt[degree + 1];

        for (int i = 0; i < terms.length; i++) {
            terms[i] = new ModularInt(0, modulus);
        }
        terms[idx] = new ModularInt(coeff, modulus);

        return new Polynomial(terms, modulus);
    }
}
