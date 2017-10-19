package assignment1;

import assignment2.ModularInt;

import java.util.Iterator;

/**
 * The main class that is responsible for starting the program.
 */
public class Main {

    private static InputReader reader;

    public static void main(String[] args) {
        if (args.length < 2) {
            throw new IllegalArgumentException("no file specified");
        }

        try {
            Main.reader = new InputReader(args[0], args[1]);
            Iterator<ExecutionCase> it = Main.reader.iterator();

            while (it.hasNext()) {
                ExecutionCase ex = it.next();
                assignment1.Number num1 = new assignment1.Number(ex.getFst(), ex.getRadix());
                assignment1.Number num2 = new assignment1.Number(ex.getSnd(), ex.getRadix());
                assignment1.Number res = null;
                Counter counter = new Counter();
                switch (ex.getOperation()) {
                    case ADD:
                        res = num1.add(num2, counter);
                        break;
                    case SUBTRACT:
                        res = num1.subtract(num2, counter);
                        break;
                    case MULTIPLY:
                        res = num1.multiply(num2, counter);
                        break;
                    case KARATSUBA:
                        res = num1.karatsuba(num2, counter);
                        break;
                    default:
                        throw new IllegalArgumentException("Unknown operation");
                }

                ex.setCalculatedResult(res.toString());
                reader.writeResult(ex, counter);
            }
        } catch (Exception e) {
            System.out.println(e);
        } finally {
            reader.close();
        }
    }
}
