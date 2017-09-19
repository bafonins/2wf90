import java.util.Arrays;
import java.util.Iterator;

/**
 * The main class that is responsible for starting the program.
 */
public class Main {

    private static InputReader reader;

    public static void main(String[] args) {
        if (args.length < 1) {
            throw new IllegalArgumentException("no file specified");
        }

        try {
            Main.reader = new InputReader(args[0]);
            Iterator<ExecutionCase> it = Main.reader.iterator();

            while (it.hasNext()) {
                ExecutionCase ex = it.next();
                Number num1 = new Number(ex.getFst(), ex.getRadix());
                Number num2 = new Number(ex.getSnd(), ex.getRadix());
                Number res = null;
                switch (ex.getOperation()) {
                    case ADD:
                        res = num1.add(num2);
                        break;
                    case SUBTRACT:
                        res = num1.subtract(num2);
                        break;
                    case MULTIPLY:
                        res = num1.multiply(num2);
                        break;
                    case KARATSUBA:
//                        res = num1.karatsuba(num2);
//                        break;
                        continue;
                    default:
                        throw new IllegalArgumentException("Unknown operation");
                }

                if (ex.getResult().isPresent()) {
                    System.out.println("answer = " + ex.getResult().get());
                    System.out.println("result = " + res);
                    System.out.println();
                }
            }
        } catch (Exception e) {
            System.out.println(e);
        } finally {
            reader.close();
        }
    }
}
