

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
            //todo
        } catch (Exception e) {
            System.out.println(e);
        } finally {
            reader.close();
        }


    }
}
