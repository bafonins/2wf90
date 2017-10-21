package assignment2;

/**
 * This class represents an integer mod p, where p is prime, p < 100.
 */
public class ModularInt {

    private int number;
    private int modulus;

    /**
     * Creates a new instance of {@code ModularInt}.
     * @param number The number;
     * @param modulo The modulo.
     */
    public ModularInt(int number, int modulo) {
        this.number = number;
        this.modulus = modulo;
    }

    public ModularInt(ModularInt obj) {
        this.number = obj.getPos();
        this.modulus = obj.getModulus();
    }

    public ModularInt add(ModularInt b) {
        this.number = (this.getPos() + b.getPos()) % this.modulus;

        return this;
    }

    public ModularInt subtract(ModularInt b) {
        this.number = (this.getPos() - b.getPos()) % this.modulus;

        return this;
    }

    public ModularInt multiply(ModularInt b) {
        this.number = (this.getPos() * b.getPos()) % this.modulus;

        return this;
    }

    public ModularInt divide(ModularInt b) {
        return this.multiply(new ModularInt(b).inverse());
    }

    /**
     * Gets a positive result after taking modulo {@code modulus}.
     * @return number `mod` modulus s.t. it is {@code >= 0}
     */
    public int getPos() {
        int n = this.number;
        if (n >= 0) {
            return n % this.modulus;
        }

        n = -n;
        if (n < this.modulus) {
            return this.modulus - n;
        }

        if (n > this.modulus) {
            return this.modulus - (n % this.modulus);
        }

        return 0;
    }

    /**
     * Gets a positive result after taking modulo {@code modulus}.
     * @return number `mod` modulus s.t. it is {@code <= 0}
     */
    public int getNeg() {
        return  - (modulus - this.getPos());
    }

    /**
     * Calculates the inverse of {@code this} `mod modulus`. Implementation of the 2.11 algorithm
     * (see lecture notes p. 18).
     * @return The inverse of {@code this} `mod modulus` or {@code null} if it does not exist.
     * Note: in the assignment m is always prime, hence there always exists an inverse -> we
     * should get {@code null} from this method.
     */
    public ModularInt inverse() {
        int a = this.getPos(); int m = this.getModulus();
        int x1 = 1; int x2 = 0;

        while (m > 0) {
            int q = a / m;
            int r = a - q * m;
            a = m; m = r;
            int x3 = x1 - q * x2;
            x1 = x2; x2 = x3;
        }

        if (a == 1) {
            return this.set(x1 % this.modulus);
        } else {
            return null;
        }
    }

    /**
     * Gets the modulus of this integer.
     * @return The modulus.
     */
    public int getModulus() {
        return this.modulus;
    }


    public ModularInt set(int n) {
        this.number = n;

        return this;
    }

    @Override
    public String toString() {
        return Integer.toString(this.getPos());
    }
}
