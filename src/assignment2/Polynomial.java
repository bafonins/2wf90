package assignment2;

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


    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("");

        if (this.terms[this.terms.length - 1] != null) {
            int last = this.terms.length - 1;
            sb.append(this.terms[last]).append("X^").append(last);
        }

        for (int i = this.terms.length - 2; i > 0; i--) {
            ModularInt coef = this.terms[i];
            if (coef != null) {
                sb.append("+").append(coef.getPos()).append("X^").append(i);
            }
        }

        ModularInt constant = this.terms[0];
        if (constant != null) {
            sb.append("+").append(constant.getPos());
        }
        
        return sb.toString();
    }
}
