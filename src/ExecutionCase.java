import java.util.Objects;
import java.util.Optional;

public class ExecutionCase {

    public enum Operation {
        ADD("[add]"),
        SUBTRACT("[subtract]"),
        MULTIPLY("multiply"),
        KARATSUBA("[karatsuba]");

        private String name;

        Operation(String name) {
            this.name = name;
        }


        public static Operation fromString(String op) {
            switch (op) {
                case "[add]":
                    return Operation.ADD;
                case "[subtract]":
                    return Operation.SUBTRACT;
                case "[multiply]":
                    return Operation.MULTIPLY;
                case "[karatsuba]":
                    return Operation.KARATSUBA;
                default:
                    throw new IllegalArgumentException("Unknown operation = " + op);
            }
        }
    }

    private int radix;
    private String fst;
    private String snd;
    private Operation operation;
    private String correctResult;
    private String calculatedResult;

    public ExecutionCase(
            int radix,
            String firstNumber,
            String secondNumber,
            Operation operation,
            String correctResult) {
        Objects.requireNonNull(firstNumber);
        Objects.requireNonNull(secondNumber);
        Objects.requireNonNull(operation);

        if (radix > 16 || radix < 2) {
            throw new IllegalArgumentException("radix cannot be < 16 or > 2");
        }

        this.radix = radix;
        this.fst = firstNumber;
        this.snd = secondNumber;
        this.operation = operation;
        this.correctResult = correctResult;
    }

    public int getRadix() {
        return radix;
    }

    public String getFst() {
        return fst;
    }

    public String getSnd() {
        return snd;
    }

    public Operation getOperation() {
        return operation;
    }

    public Optional<String> getCorrectResult() {
        return Optional.ofNullable(this.correctResult);
    }

    public Optional<String> getCalculatedResult() {
        return Optional.ofNullable(this.calculatedResult);
    }

    public void setCalculatedResult(String calculatedResult) {
        this.calculatedResult = calculatedResult;
    }
}
