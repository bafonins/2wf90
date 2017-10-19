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
    private int[] terms;

    public Polynomial(int[] terms) {
        Objects.requireNonNull(terms);

        this.terms = terms;
    }


    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("");

        if (this.terms[this.terms.length - 1] != 0) {
            int last = this.terms.length - 1;
            sb.append(this.terms[last]).append("X^").append(last);
        }

        for (int i = this.terms.length - 2; i > 0; i--) {
            int coef = this.terms[i];
            if (coef != 0) {
                String term = coef + "X^" + i;
                if (coef > 0) { sb.append("+").append(term); }
                else { sb.append(term); }
            }
        }

        int constant = this.terms[0];
        if (constant != 0) {
            if (constant > 0) { sb.append("+").append(constant); }
            else { sb.append(constant); }
        }
        
        return sb.toString();
    }
}
