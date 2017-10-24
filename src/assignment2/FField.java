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
    public Polynomial[] elements;

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
            l.add(new Polynomial(ts, m));
            return;
        }

        for (int i = m; i < this.p; i++) {
            for (int j = idx; j <= ofDegree; j++) {
                ts[j] = new ModularInt(i, this.p);
            }
            generateOfDegree(ofDegree, m + 1, idx + 1, l, ts.clone());
        }
    }

    public Polynomial[][] produceAdditionTable() {

        return null;
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
