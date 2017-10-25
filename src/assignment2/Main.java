package assignment2;

import java.util.Scanner;

public class Main {

    private static Scanner scan = new Scanner(System.in);

    public static void main(String[] args) {

        printDescription();

        while (true) {
            printCommands();
            int op = scan.nextInt();
            int p, number, degree;
            ModularInt[] ints;
            Polynomial poly1, poly2, poly3;
            Polynomial[] r;
            switch (op) {
                case 1:
                    p = readModulus();
                    number = readNumber();
                    ModularInt result = new ModularInt(number, p);
                    System.out.println(number + " \u2630 " + result + " mod " + p);
                    break;
                case 2:
                    p = readModulus();
                    degree = readDegree();
                    poly1 = readPolynomial(degree, p);
                    System.out.println("Result = " + poly1);
                    break;
                case 3:
                    p = readModulus();
                    degree = readDegree();
                    poly1 = readPolynomial(degree, p);
                    degree = readDegree();
                    poly2 = readPolynomial(degree, p);

                    System.out.println("Result = " + poly1.sum(poly2));
                    break;
                case 4:
                    p = readModulus();
                    degree = readDegree();
                    poly1 = readPolynomial(degree, p);
                    degree = readDegree();
                    poly2 = readPolynomial(degree, p);
                    System.out.println("Result = " + poly1.difference(poly2));
                    break;
                case 5:
                    p = readModulus();
                    degree = readDegree();
                    poly1 = readPolynomial(degree, p);
                    degree = readDegree();
                    poly2 = readPolynomial(degree, p);
                    System.out.println("Result = " + poly1.product(poly2));
                    break;
                case 6:
                    p = readModulus();
                    number = readNumber();
                    degree = readDegree();
                    poly1 = readPolynomial(degree, p);
                    System.out.println("Result = " + poly1.scalarMultiple(number));
                    break;
                case 7:
                    p = readModulus();
                    degree = readDegree();
                    poly1 = readPolynomial(degree, p);
                    degree = readDegree();
                    poly2 = readPolynomial(degree, p);
                    r = poly1.longDivision(poly2);
                    System.out.printf("Result = ");
                    System.out.println("quotient = " + r[0]);
                    System.out.println("remainder = " + r[1]);
                    break;
                case 8:
                    p = readModulus();
                    degree = readDegree();
                    poly1 = readPolynomial(degree, p);
                    degree = readDegree();
                    poly2 = readPolynomial(degree, p);
                    System.out.println("gcd(" + poly1 + ", " + poly2 + ") = " + Polynomial.GCD(poly1, poly2));
                    break;
                case 9:
                    p = readModulus();
                    degree = readDegree();
                    poly1 = readPolynomial(degree, p);
                    degree = readDegree();
                    poly2 = readPolynomial(degree, p);
                    r = Polynomial.extendedGCD(poly1, poly2);
                    System.out.println("Result = ");
                    System.out.println(r[0] + " * " + poly1 + " + " + r[1] + " * " + poly2 + " = " + Polynomial.GCD(poly1, poly2));
                    break;
                case 10:
                    p = readModulus();
                    degree = readDegree();
                    poly1 = readPolynomial(degree, p);
                    degree = readDegree();
                    poly2 = readPolynomial(degree, p);
                    degree = readDegree();
                    poly3 = readPolynomial(degree, p);
                    System.out.println("Are congruent = " + Polynomial.congruent(poly1, poly2, poly3));
                    break;
                default:
                    return;

            }
        }
    }

    private static void printDescription() {
        System.out.println("==================================================");
        System.out.println("\u2192 For more precise guidelines on how to interact");
        System.out.println("  with the program - read the report.");
        System.out.println("==================================================");
        System.out.println();
    }

    private static void printCommands() {
        System.out.println("=============== LIST OF COMMANDS =================");
        System.out.println("\u21b3 Type '1' to calculate integer mod p, ");
        System.out.println("  where p is prime.");
        System.out.println("\u21b3 Type '2' to calculate polynomial mod p, ");
        System.out.println("  where p is prime. ");
        System.out.println("\u21b3 Type '3' to add two polynomials. (1st + 2nd) ");
        System.out.println("\u21b3 Type '4' to subtract two polynomials. (1st - 2nd)");
        System.out.println("\u21b3 Type '5' to multiply two polynomials. (1st * 2nd)");
        System.out.println("\u21b3 Type '6' to multiply a polynomials with.");
        System.out.println("\u21b3 Type '7' to execute the long division algorithm on ");
        System.out.println("  two polynomials. (1st / 2nd)");
        System.out.println("\u21b3 Type '8' to calculate the GCD of two polynomials.");
        System.out.println("\u21b3 Type '9' to calculate the extended GCD of two");
        System.out.println("  polynomials. (x * 1st + y * 2nd = gcd(1st, 2nd))");
        System.out.println("\u21b3 Type '10' to decide whether two polynomials in");
        System.out.println(" the mod p setting are equal modulo a third polynomial.");
        System.out.println("  (1st \u2630 3rd (mod p) and 2nd \u2630 3rd (mod p))");






        System.out.println("==================================================");

    }



    private static Polynomial readPolynomial(int degree, int p) {
        System.out.print("Input polynomial as a sequence of coefficients = ");
        ModularInt[] ts = new ModularInt[degree + 1];
        try {
            for (int i = 0; i <= degree; i++) {
                ts[i] = new ModularInt(scan.nextInt(), p);
            }

            return new Polynomial(ts, p);
        } catch (Exception e) {
            System.out.println("Error occurred when reading the input");
        }

        return null;

    }

    private static int readDegree() {
        System.out.print("Input a degree of the polynomial d \u2265 0 = ");
        return scan.nextInt();
    }

    private static int readModulus() {
        System.out.println("Input modulus p, where 1 \u2264 p < 100");
        return scan.nextInt();
    }

    private static int readNumber() {
        System.out.println("Input a number = ");
        return scan.nextInt();
    }

}
