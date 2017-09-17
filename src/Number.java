import java.util.Objects;

/**
 * Represents a single number to perform calculations on.
 * This class is immutable.
 */
public class Number implements Comparable<Number> {

    private static String[] values = {
            "0",
            "1",
            "2",
            "3",
            "4",
            "5",
            "6",
            "7",
            "8",
            "9",
            "A",
            "B",
            "C",
            "D",
            "E",
            "F"
    };

    private boolean isPositive = true;
    private int base;
    private int[] words;

    /**
     * Initializes a new instance of {@code Number}.
     * @param number String representation of {@code Number} to instantiate.
     * @param base The base of {@code Number} to instantiate.
     */
    public Number(String number, int base) {
        Objects.requireNonNull(number);

        if (number.isEmpty()) {
            throw new IllegalArgumentException("String representation of a number cannot be empty");
        }

        if (base > 16 || base < 2) {
            throw new IllegalArgumentException("Base can take values only within the range of [2, 16]" +
                    " current value is = " + base);
        }

        this.base = base;
        this.words = this.getWords(number);
        this.isPositive = '-' != number.charAt(0);
    }

    /**
     * Initializes a new instance of {@code Number}. Use this constructor to create the
     * resulting number within this class.
     * @param words An arrays of words that define the number to instantiate.
     * @param base The base of {@code Number} to instantiate.
     * @param isPositive A flag defining if the {@code Number} is positive.
     */
    private Number(int[] words, int base, boolean isPositive) {
        this.words = words;
        this.base = base;
        this.isPositive = isPositive;
    }

    /**
     * Adds two {@code Number}.
     * @param num The number to add with {@code this}.
     * @return A new {@code Number}, which is the result of addition.
     */
    public Number add(Number num) {
        validateBase(this, num);

        // delegate to subtraction
        if (this.isPositive() && !num.isPositive()) {
            return this.subtract(num);
        }

        //delegate to subtraction
        if (!this.isPositive() && num.isPositive()) {
            return num.subtract(this);
        }

        // add and change the sign
        if (!this.isPositive() && !num.isPositive()) {
            // make both number positive, add and change the final sign
            Number pos1 = this.negate();
            Number pos2 = num.negate();

            return pos1.add(pos2).negate();
        }

        // continue with a simple addition when two number are positive

        ResizeResult resizedResult = new ResizeResult(this, num, false);
        int[] num1 = resizedResult.getResizedNum1();
        int[] num2 = resizedResult.getResizedNum2();
        int[] res = resizedResult.getResizedResult();
        int b = this.getBase();
        int c = 0; // carry

        for (int i = 0; i < res.length - 1; i++) {
            res[ i ] = num1[ i ] + num2[ i ] + c;
            if (res[ i ] >= b) {
                res[ i ] -= b;
                c = 1;
            } else {
                c = 0;
            }
        }

        if (c == 1) {
            res[ res.length - 1 ] = 1;
        }

        return new Number(res, b, true);
    }


    /**
     * Subtracts from {@code this}.
     * @param num The number to subtract from {@code this}.
     * @return A new {@code Number}, which is the result of subtraction.
     */
    public Number subtract(Number num) {
        validateBase(this, num);

        // simply add, num1 - (-num2) = num1 + num2
        if (this.isPositive() && !num.isPositive()) {
            return num.negate().add(this);
        }

        // make the first number positive, add with the second one and change
        // the final sign.
        if (!this.isPositive() && num.isPositive()) {
            return this.negate().add(num).negate();
        }

        // call subtract on the second number, since
        // -num1 - (-num2) = num2 - num1
        if (!this.isPositive() && !num.isPositive()) {
            return num.subtract(this.negate());
        }

        // equal number, simply return 0
        if (this.compareTo(num) == 0) {
            return new Number(new int[]{ 0 }, this.base, false);
        }

        // we want the first number to be larger than the second one
        // if not, flip the operands
        if (this.compareTo(num) < 0) {
            return num.subtract(this).negate();
        }

        // continue with a simple subtraction when two number are positive

        ResizeResult resizedResult = new ResizeResult(this, num, false);
        int[] num1 = resizedResult.getResizedNum1();
        int[] num2 = resizedResult.getResizedNum2();
        int[] res = resizedResult.getResizedResult();
        int b = this.getBase();
        int c = 0; // carry

        for (int i = num1.length - 1; i >= 0; i--) {
            res[ i ] = num1[ i ] - num2[ i ] - c;
            if (res[ i ] < 0) {
                res[ i ] += b;
                c = 1;
            } else {
                c = 0;
            }
        }

        return new Number(res, this.base, true);
    }

    /**
     * Multiplies two {@code Number}.
     * @param num The number to multiply with {@code this}.
     * @return A new {@code Number}, which is the result of multiplication.
     */
    public Number miltiply(Number num) {
        validateBase(this, num);

        ResizeResult resizedResult = new ResizeResult(this, num, true);
        int[] num1 = resizedResult.getResizedNum1();
        int[] num2 = resizedResult.getResizedNum2();
        int[][] intermediate = new int[ num1.length ][ num1.length  + 1 ];
        int b = num.getBase();

        // first generate intermediate number after multiplication
        for (int i = 0; i < num1.length; i++) {
            int c = 0;
            for (int j = 0; j < num2.length; j++) {
                int t = num1[ i ] * num2[ j ] + c;
                c = t / b;
                intermediate[ i ][ j ] = t - c * b;
            }
            intermediate[ i ][ intermediate[ i ].length - 1 ] = c;
        }

        // then simply sum them up by shifting the next number by one
        Number result = new Number(intermediate[ 0 ], b, true);
        for (int i = 1; i < intermediate.length; i++) {
            int[] addWith = new Number(intermediate[ i ], b, true).rebase(result.getLength() + 1, i);
            result = result.add(new Number(addWith, b, true));
        }

        return result;
    }

    public Number karatsuba(Number n) {
        validateBase(this, n);
        return null;
    }


    /**
     * Gets the base of the number.
     * @return The base of the number.
     */
    public int getBase() {
        return this.base;
    }

    /**
     * Gets the length of {@code Number}.
     * @return The length of {@code Number}.
     */
    public int getLength() {
        return this.words.length;
    }

    /**
     * Returns a copy of the words field of {@code this} number.
     * @return A copy of the words field of {@code this} number.
     */
    public int[] getWords() {
        return this.words.clone();
    }

    /**
     * Defines whether {@code this} is positive.
     * @return {@code true} if the number is positive, {@code false otherwise}.
     */
    public boolean isPositive() {
        return this.isPositive;
    }

    public int[] rebase(int size, int shift) {
        if (size < this.words.length) {
            throw new IllegalArgumentException("Cannot perform rebase, since size(" +
                    size + ") is smaller than the size of the current number(" + this.words.length + ")");
        }

        int[] newWords = new int[size + shift];
        for (int i = 0; i < this.words.length; i++) {
            newWords[ i + shift ] = this.words[ i ];
        }

        return newWords;
    }

    public Number negate() {
        return new Number(this.words, this.base, !this.isPositive);
    }

    @Override
    public String toString() {
        int i = this.words.length - 1;
        while (i >= 0 && this.words[ i ] == 0) { i--; }

        if (i == -1) { return "0"; }
        StringBuilder sb = new StringBuilder(this.isPositive() ? "" : "-");
        for (int j = i; j >= 0 ; j--) {
            sb.append(values[ this.words[ j ] ]);
        }

        return sb.toString();
    }

    private int[] getWords(String number) {
        int length = number.length();
        int[] ws = new int[length];

        for (int i = length - 1; i >= 0; i--) {
            char ch = number.charAt(i);

            // use ascii table to parse the string
            int range = ch - 48; // check if number
            if (range >= 0 && range <= 9) {
                ws[ length - i - 1 ] = range;
                continue;
            }

            range = ch - 55; // check if A-F
            if (range >= 10 && range <= 15) {
                ws[ length - i - 1 ] = range;
                continue;
            }

            // skip the '-' sign
            if (ch == '-') { continue; }

            throw new RuntimeException("Invalid character = " + ch);
        }

        return ws;
    }

    private void validateBase(Number a, Number b) {
        if (a.getBase() != b.getBase()) {
            throw new IllegalArgumentException("Number have different bases");
        }
    }

    @Override
    public int compareTo(Number o) {
        this.validateBase(this, o);

        int val1 = 0;
        for (int i = 0; i < this.words.length; i++) {
            val1 += this.words[i] * ((int) Math.pow(this.base, i));
        }

        int val2 = 0;
        for (int i = 0; i < o.words.length; i++) {
            val2 += o.words[i] * ((int) Math.pow(o.getBase(), i));
        }

        return Integer.compare(val1, val2);
    }
}
