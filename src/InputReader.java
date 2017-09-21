
import java.io.Closeable;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Iterator;
import java.util.Objects;
import java.util.Optional;
import java.util.Scanner;

public class InputReader implements Closeable, Iterable<ExecutionCase> {

    private Scanner sc;
    private PrintWriter wr;


    public InputReader(String inputPath, String outputPath) {
        Objects.requireNonNull(inputPath);
        Objects.requireNonNull(outputPath);

        if (inputPath.isEmpty()) {
            throw new IllegalArgumentException("The file path cannot be empty");
        }

        this.init(new File(inputPath), new File(outputPath));
    }

    public void writeResult(ExecutionCase exCase) {
        Objects.requireNonNull(exCase);
        this.wr.println("[operation]\t\t\t" + exCase.getOperation());
        this.wr.println("[x]\t\t\t\t\t" + exCase.getFst());
        this.wr.println("[y]\t\t\t\t\t" + exCase.getSnd());
        exCase.getCorrectResult().ifPresent(knownCorrectResult -> {
            this.wr.println("[known result]\t\t" + knownCorrectResult);
        });
        this.wr.println("[calculated result] " + exCase.getCalculatedResult()
                .orElseThrow(() -> new RuntimeException("the calculated result is not set")));
        this.wr.println();
    }

    @Override
    public void close() {
        if (this.sc != null) {
            this.sc.close();
        }

        if (this.wr != null) {
            this.wr.close();
        }
    }

    private void init(File out, File in) {
        try {
            this.sc = new Scanner(out);
        } catch (FileNotFoundException e) {
            System.err.print("Failed to init scanner with file = " + out.getAbsolutePath());
            throw new RuntimeException();
        }

        try {
            this.wr = new PrintWriter(in);
        } catch (FileNotFoundException e) {
            System.err.print("Failed to init writer with file = " + in.getAbsolutePath());
            throw new RuntimeException();
        }
    }

    @Override
    public Iterator<ExecutionCase> iterator() {
        return new InputReaderIterator();
    }

    private class InputReaderIterator implements Iterator<ExecutionCase> {

        private static final String RADIX_TOKEN = "[radix]";
        private static final String X_TOKEN = "[x]";
        private static final String Y_TOKEN = "[y]";


        private Scanner scan;
        private Optional<ExecutionCase> next;

        public InputReaderIterator() {
            this.scan = InputReader.this.sc;
            this.next = this.buildExecutionCase();
        }

        @Override
        public boolean hasNext() {
            return next.isPresent();
        }

        @Override
        public ExecutionCase next() {
            Optional<ExecutionCase> curr = this.next;
            this.next = this.buildExecutionCase();

            return curr.get();
        }

        private Optional<ExecutionCase> buildExecutionCase() {
            if (!this.scan.hasNext()) {
                return Optional.empty();
            }

            String radixToken = this.scan.next();
            if (!radixToken.equalsIgnoreCase(RADIX_TOKEN)) {
                throw new RuntimeException("expected '" + RADIX_TOKEN + "', but got = '" + radixToken + "'");
            }

            int radix = this.scan.nextInt();
            ExecutionCase.Operation operation = ExecutionCase.Operation.fromString(this.scan.next());

            String xToken = this.scan.next();
            if (!xToken.equals(X_TOKEN)) {
                throw new RuntimeException("expected '" + X_TOKEN + "', but got = '" + xToken + "'");
            }
            String xNumber = this.scan.next();

            String yToken = this.scan.next();
            if (!yToken.equals(Y_TOKEN)) {
                throw new RuntimeException("expected '" + Y_TOKEN +"', but got = '" + yToken + "'");
            }
            String yNumber = this.scan.next();

            String result = null;
            if (this.scan.hasNext("#")) {
                this.scan.next(); this.scan.next();
                result = this.scan.next();
            }

            return Optional.of(new ExecutionCase(
                    radix,
                    xNumber,
                    yNumber,
                    operation,
                    result
            ));
        }
    }
}
