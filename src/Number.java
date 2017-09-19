import java.util.Arrays;
import java.util.Objects;

/**
 * Represents a single number to perform calculations on.
 * This class is immutable.
 */
public class Number implements Comparable<Number> {

    /**
     * An array of all possible values that can appear within this assignment.
     * This table is used to map raw integers to a proper string representation.
     */
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
            "a",
            "b",
            "c",
            "d",
            "e",
            "f"
    };

    /**
     * Words of {@code this} number. Note, that {@code words[0]} holds the last digit in a number.
     * So, the number '123' is stored as words[0] = 3, words[1] = 2, words[2] = 1.
     * This is done for convenient output and to simplify operations.
     */
    private int[] words;
    private boolean isPositive = true;
    private int base;

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
        this.isPositive = ('-' != number.charAt(0));
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
            return this.subtract(num.negate());
        }

        //delegate to subtraction
        if (!this.isPositive() && num.isPositive()) {
            return num.subtract(this.negate());
        }

        // add and change the sign
        if (!this.isPositive() && !num.isPositive()) {
            // make both number positive, add and change the final sign
            Number pos1 = this.negate();
            Number pos2 = num.negate();

            return pos1.add(pos2).negate();
        }

        // continue with a simple addition when two number are positive
        int n = this.getLength() > num.getLength() ? this.getLength() : num.getLength();
        int[] num1 = this.rebaseRight(n, 0);
        int[] num2 = num.rebaseRight(n, 0);
        int[] res = new int[ n + 1 ];
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
            return num.negate().subtract(this.negate());
        }

        // we want the first number to be larger than the second one
        // if not, flip the operands
        if (this.compareTo(num) < 0) {
            return num.subtract(this).negate();
        }

        // continue with a simple subtraction when two number are positive

        int n = this.getLength() > num.getLength() ? this.getLength() : num.getLength();
        int[] num1 = this.rebaseRight(n, 0);
        int[] num2 = num.rebaseRight(n, 0);
        int[] res = new int[ n ];
        int b = this.getBase();
        int c = 0; // carry

        for (int i = 0; i < num1.length; i++) {
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
    public Number multiply(Number num) {
        validateBase(this, num);

        int n = this.getLength() > num.getLength() ? this.getLength() : num.getLength();
        int[] num1 = this.rebaseLeft(n, 0);
        int[] num2 = num.rebaseLeft(n, 0);
        int[][] intermediate = new int[ n ][ n  + 1 ];
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

        // then simply sum them up by shifting the next number by i
        Number result = new Number(intermediate[ 0 ], b, true);
        for (int i = 1; i < intermediate.length; i++) {
            int[] addWith = new Number(intermediate[ i ], b, true).rebaseLeft(result.getLength() + 1, i);
            result = result.add(new Number(addWith, b, true));
        }

        // check if the sign must be changed. pos XOR neg -> true
        return (this.isPositive() ^ num.isPositive()) ? result.negate() : result;
    }

    /**
     * Multiplies two {@code Number} using the Karatsuba method.
     * @param num The number to multiply with {@code this}.
     * @return A new {@code Number}, which is the result of multiplication.
     */
    public Number karatsuba(Number num) {
        validateBase(this, num);

//        if (!this.isPositive()) {
//            this.isPositive = true;
//        }
//
//        if (!num.isPositive()) {
//            num = num.negate();
//        }
//
//        // base case
//        if (this.getLength() == 1 && num.getLength() == 1) {
//            return this.multiply(num);
//        }
//
//        ResizeResult resizedResult = new ResizeResult(this, num, true);
//        int base = this.getBase();
//        int[] num1 = resizedResult.getResizedNum1();
//        int[] num2 = resizedResult.getResizedNum2();
//        int size = num1.length;
//
//        System.out.println(Arrays.toString(num1));
//        System.out.println(Arrays.toString(num2));
//
//        // the most significant part of the first number
//        Number a = new Number(Arrays.copyOfRange(num1, size / 2, size), base, true);
//        // the least significant part of the first number
//        Number b = new Number(Arrays.copyOfRange(num1, 0, size / 2), base, true);
//        // the most significant part of the second number
//        Number c = new Number(Arrays.copyOfRange(num2, size / 2, size), base, true);
//        // the least significant part of the second number
//        Number d = new Number(Arrays.copyOfRange(num2, 0, size / 2), base, true);
//
//        System.out.println("a = " + a);
//        System.out.println("b = " + b);
//        System.out.println("c = " + c);
//        System.out.println("d = " + d);
//        System.out.println();
//
//        // do (a * c) and (b * d)
//        Number ac = a.karatsuba(c);
//        Number db = d.karatsuba(b);
//
//        // do (a + b) * (c + d)
//        Number abcd = a.add(b).karatsuba(d.add(c));
//
//        // do (a + d) * (b + c) - a * c - b * d
//        Number z = abcd.subtract(ac).subtract(db);
//
////        return ac.add(abcd.subtract(ac).subtract(bd).shiftLeft(N)).add(bd.shiftLeft(2*N));
//
//        Number x = new Number(z.rebaseRight(size, size), base, true);
//        Number y = new Number(db.rebaseRight(size, size * 2), base, true);
//
//        return ac.add(x.add(y));

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


    /**
     * Makes a new array of words from {@code this} number of length {@code size} and optionally
     * shift the words by {@shift} cells from the left. It can be used to acquire a copy of the existing array,
     * to extend it or fill the least significant bits with 0.
     * For example, [1,2,3] -> rebaseLeft(3, 2) -> [0,0,1,2,3]
     * @param size The size of a new array.
     * @param shift The number of cells to shift the numbers to the right.
     * @return A new array of length {@code size} shifted by {@code shift} cells to the left.
     */
    public int[] rebaseLeft(int size, int shift) {
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

    /**
     * Makes a new array of words from {@code this} number of length {@code size} and optionally
     * shift the words by {@shift} cells from the right. It can be used to acquire a copy of the existing array,
     * to extend it or fill the most significant bits with 0.
     * For example, [1,2,3] -> rebaseRight(3, 2) -> [1,2,3,0,0]
     * @param size The size of a new array.
     * @param shift The number of cells to shift the numbers to the left.
     * @return A new array of length {@code size} shifted by {@code shift} cells to the left.
     */
    public int[] rebaseRight(int size, int shift) {
        if (size < this.words.length) {
            throw new IllegalArgumentException("Cannot perform rebase, since size(" +
                    size + ") is smaller than the size of the current number(" + this.words.length + ")");
        }

        int[] newWords = new int[size + shift];
        for (int i = 0; i < this.words.length; i++) {
            newWords[ i ] = this.words[ i ];
        }

        return newWords;
    }

    /**
     * Changes the sing of {@code this} number.
     * @return Negated version of {@code this} number.
     */
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

            range = ch - 87; // check if a-f
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


        if (this.isPositive() && !o.isPositive()) {
            return 1;
        }

        if (!this.isPositive() && o.isPositive()) {
            return -1;
        }

        boolean bothNegative = !this.isPositive() && !o.isPositive();

        int i = this.words.length - 1;
        int j = o.words.length - 1;

        // eliminate redundant zeros in the beginning
        while (i >= 0 && this.words[i] == 0) { i--; }
        while (j >= 0 && o.words[j] == 0) { j--; }

        if (i == -1 && j == -1) { // both are zeros
            return 0;
        } else if (i == -1 && j != -1) { // first is zero
            return o.isPositive() ? -1 : 1;
        } else if (i != -1 && j == -1) { // second is zero
            return this.isPositive() ? 1 : -1;
        }

        // check for length
        if (i > j && bothNegative) {
            return -1;
        } else if (i < j && bothNegative) {
            return 1;
        } else if (i > j) {
            return 1;
        } else if (i < j) {
            return -1;
        }

        int res = 0;

        while (i >= 0 && j >= 0) {
            if (this.words[i] > o.words[j] && bothNegative) {
                return -1;
            } else if (this.words[i] < o.words[j] && bothNegative) {
                return 1;
            } else if (this.words[i] > o.words[j]) {
                return 1;
            } else if (this.words[i] < o.words[j]) {
                return -1;
            }
            i--; j--;
        }

        return res;
    }
}
