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

        for (int i = 0; i < n_max; i++) {
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

        for (int i = 0; i < n_max; i++) {
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

        for (int i = 0; i < n_max; i++) {
            for (int j = 0; j < n_max; j++) {
                result[i].add(this.terms[i].multiply(b.terms[i]));
            }
        }

        return this;
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
}
