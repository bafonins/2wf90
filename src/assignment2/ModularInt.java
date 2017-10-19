package assignment2;

/**
 * This class represents an integer mod p, where p is prime, p < 100.
 */
public class ModularInt {

    private int number;
    private int m;

    /**
     * Creates a new instance of {@code ModularInt}.
     * @param number The number;
     * @param modulo The modulo.
     */
    public ModularInt(int number, int modulo) {
        this.number = number;
        this.m = modulo;
    }

    /**
     * Gets a positive result after taking modulo {@code m}.
     * @return number `mod` m s.t. it is {@code >= 0}
     */
    public int getPos() {
        int n = this.number;
        if (n >= 0) {
            return n % this.m;
        }

        n = -n;
        if (n < m) {
            return m - n;
        }

        if (n > m) {
            return m - (n % m);
        }

        return 0;
    }

    /**
     * Gets a positive result after taking modulo {@code m}.
     * @return number `mod` m s.t. it is {@code <= 0}
     */
    public int getNeg() {
        return  - (m - this.getPos());
    }
}
