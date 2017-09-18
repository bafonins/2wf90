import java.util.Objects;

/**
 * This class encapsulates number placeholders required for calculations.
 */
public class ResizeResult {

    private int[] resizedNum1;
    private int[] resizedNum2;
    private int[] resizedResult; // is null in case of multiplication

    /**
     * Initializes a new instance of {@code ResizeResult}
     * @param num1 The first number.
     * @param num2 The second number
     * @param mult A flag defining whether placeholders must be created for the multiplication operation.
     */
    public ResizeResult(Number num1, Number num2, boolean mult) {
        Objects.requireNonNull(num1);
        Objects.requireNonNull(num2);

        if (num1.getLength() == num2.getLength()) {
            this.resizedNum1 = num1.rebaseLeft(num1.getLength(), 0);
            this.resizedNum2 = num2.rebaseLeft(num2.getLength(), 0);
            this.resizedResult = mult ? null : new int[ num1.getLength() + 1 ];
        } else if (num1.getLength() > num2.getLength()) {
            this.resizedNum1 = num1.rebaseLeft(num1.getLength(), 0);
            this.resizedNum2 = num2.rebaseLeft(num1.getLength(), 0);
            this.resizedResult = mult ? null : new int[ num1.getLength() + 1 ];
        } else {
            this.resizedNum1 = num1.rebaseLeft(num2.getLength(), 0);
            this.resizedNum2 = num2.rebaseLeft(num2.getLength(), 0);
            this.resizedResult = mult ? null : new int[ num2.getLength() + 1 ];
        }

    }

    public int[] getResizedNum1() {
        return resizedNum1;
    }

    public int[] getResizedNum2() {
        return resizedNum2;
    }

    public int[] getResizedResult() {
        return resizedResult;
    }
}
