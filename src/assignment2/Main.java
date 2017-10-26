package assignment2;

import java.util.Scanner;

public class Main {

    private static Scanner scan = new Scanner(System.in);

    public static void main(String[] args) {

        printDescription();

        while (true) {
            printCommands();
            System.out.print("Operation: ");
            int op = scan.nextInt();
            int p, number, degree;
            ModularInt[] ints;
            Polynomial poly1, poly2, poly3;
            Polynomial[] r;
            FField f;
            switch (op) {
                case 1:
                    p = readModulus();
                    number = readNumber();
                    ModularInt result = new ModularInt(number, p);
                    System.out.println("\u21e8 Result = " + number + " \u2630 " + result + " mod " + p);
                    break;
                case 2:
                    p = readModulus();
                    degree = readDegree();
                    poly1 = readPolynomial(degree, p);
                    System.out.println("\u21e8 Result = " + poly1);
                    break;
                case 3:
                    p = readModulus();
                    degree = readDegree();
                    poly1 = readPolynomial(degree, p);
                    degree = readDegree();
                    poly2 = readPolynomial(degree, p);

                    System.out.println("\u21e8 Result = " + poly1.sum(poly2));
                    break;
                case 4:
                    p = readModulus();
                    degree = readDegree();
                    poly1 = readPolynomial(degree, p);
                    degree = readDegree();
                    poly2 = readPolynomial(degree, p);
                    System.out.println("\u21e8 Result = " + poly1.difference(poly2));
                    break;
                case 5:
                    p = readModulus();
                    degree = readDegree();
                    poly1 = readPolynomial(degree, p);
                    degree = readDegree();
                    poly2 = readPolynomial(degree, p);
                    System.out.println("\u21e8 Result = " + poly1.product(poly2));
                    break;
                case 6:
                    p = readModulus();
                    number = readNumber();
                    degree = readDegree();
                    poly1 = readPolynomial(degree, p);
                    System.out.println("\u21e8 Result = " + poly1.scalarMultiple(number));
                    break;
                case 7:
                    p = readModulus();
                    degree = readDegree();
                    poly1 = readPolynomial(degree, p);
                    degree = readDegree();
                    poly2 = readPolynomial(degree, p);
                    r = poly1.longDivision(poly2);
                    System.out.printf("\u21e8 Result = ");
                    System.out.println("   quotient = " + r[0]);
                    System.out.println("   remainder = " + r[1]);
                    break;
                case 8:
                    p = readModulus();
                    degree = readDegree();
                    poly1 = readPolynomial(degree, p);
                    degree = readDegree();
                    poly2 = readPolynomial(degree, p);
                    System.out.println("\u21e8 gcd(" + poly1 + ", " + poly2 + ") = " + Polynomial.GCD(poly1, poly2));
                    break;
                case 9:
                    p = readModulus();
                    degree = readDegree();
                    poly1 = readPolynomial(degree, p);
                    degree = readDegree();
                    poly2 = readPolynomial(degree, p);
                    r = Polynomial.extendedGCD(poly1, poly2);
                    System.out.println("\u21e8 Result = ");
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
                    System.out.println("\u21e8 Are congruent = " + Polynomial.congruent(poly1, poly2, poly3));
                    break;
                case 11:
                    p = readModulus();
                    degree = readDegree();
                    poly1 = readFieldPolynomial(degree, p);
                    f = new FField(p, poly1);
                    System.out.println("\u21e8 Field elements : " + f);
                    System.out.println("\u21e8 Addition table in Z/" + p + "/(" + poly1 + ") :");
                    printTable(f, f.produceAdditionTable());
                    break;
                case 12:
                    p = readModulus();
                    degree = readDegree();
                    poly1 = readFieldPolynomial(degree, p);
                    f = new FField(p, poly1);
                    System.out.println("\u21e8 Field elements : " + f);
                    System.out.println("\u21e8 Multiplication table in Z/" + p + "/(" + poly1 + ") :");
                    printTable(f, f.produceMultiplicationTable());
                    break;
                case 13:
                    p = readModulus();
                    degree = readDegree();
                    poly1 = readFieldPolynomial(degree, p);
                    degree = readDegree();
                    poly2 = readPolynomial(degree, p);
                    poly3 = readPolynomial(degree, p);
                    f = new FField(p, poly1);
                    System.out.println("\u21e8 Result = " + f.sum(poly2, poly3));
                    break;
                case 14:
                    p = readModulus();
                    degree = readDegree();
                    poly1 = readFieldPolynomial(degree, p);
                    degree = readDegree();
                    poly2 = readPolynomial(degree, p);
                    poly3 = readPolynomial(degree, p);
                    f = new FField(p, poly1);
                    System.out.println("\u21e8 Result = " + f.product(poly2, poly3));
                    break;
                case 15:
                    p = readModulus();
                    degree = readDegree();
                    poly1 = readFieldPolynomial(degree, p);
                    degree = readDegree();
                    poly2 = readPolynomial(degree, p);
                    poly3 = readPolynomial(degree, p);
                    f = new FField(p, poly1);
                    System.out.println("\u21e8 Result = " + f.quotent(poly2, poly3));
                    break;
                case 16:
                    break;
                case 17:
                    break;
                case 18:
                    return;
                default:
                    System.out.println("Wrong operation code.");

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
        System.out.println("\u21b3 Type '11' to produce an addition table of a field");
        System.out.println("\u21b3 Type '12' to produce a multiplication table of a field");
        System.out.println("\u21b3 Type '13' to add two polynomials in a field. (1st + 2nd)");
        System.out.println("\u21b3 Type '14' to multiply two polynomials in a field. (1st * 2nd)");
        System.out.println("\u21b3 Type '15' to calculate q quotient of two polynomials. (1st * 2nd^-1)");
        System.out.println("\u21b3 Type '16' TODO");
        System.out.println("\u21b3 Type '17' TODO");
        System.out.println("\u21b3 Type '18' to stop the program");









        System.out.println("==================================================");

    }



    private static Polynomial readPolynomial(int degree, int p) {
        System.out.print("Input polynomial as a sequence of coefficients = ");
        ModularInt[] ts = new ModularInt[degree + 1];
        try {
            for (int i = 0; i <= degree; i++) {
                ts[degree - i] = new ModularInt(scan.nextInt(), p);
            }

            Polynomial pp = new Polynomial(ts, p);

            System.out.println("Your input polynomial: " + getInputPolynomial(ts));
            return pp;
        } catch (Exception e) {
            System.out.println("Error occurred when reading the input");
        }

        return null;

    }

    private static Polynomial readFieldPolynomial(int degree, int p) {
        System.out.print("Input polynomial for the field as a sequence of coefficients = ");
        ModularInt[] ts = new ModularInt[degree + 1];
        try {
            for (int i = 0; i <= degree; i++) {
                ts[degree - i] = new ModularInt(scan.nextInt(), p);
            }

            Polynomial pp = new Polynomial(ts, p);

            System.out.println("Your input polynomial: " + getInputPolynomial(ts));
            return pp;
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
        System.out.println("Input modulus p, where 1 < p < 100");
        return scan.nextInt();
    }

    private static int readNumber() {
        System.out.println("Input a number = ");
        return scan.nextInt();
    }

    private static String getInputPolynomial(ModularInt[] ts) {
        if (ts.length == 1) { return ts[0].toString(); }

        StringBuilder sb = new StringBuilder("");

        int fst = ts[ts.length - 1].getRawValue();
        if (fst > 0) {
            sb.append(fst).append("X^").append(ts.length - 1);
        } else if (fst < 0) {
            sb.append(fst).append("X^").append(ts.length - 1);
        }

        for (int i = ts.length - 2; i > 0; i--) {
            int val = ts[i].getRawValue();
            if (val > 0) {
                sb.append("+").append(val).append("X^").append(i);
            } else if (val < 0) {
                sb.append(val).append("X^").append(i);
            }
        }

        int constant = ts[0].getRawValue();
        if (constant > 0) {
            sb.append("+").append(constant);
        } else if (constant < 0) {
            sb.append(constant);
        }

        return sb.toString();
    }

    private static void printTable(FField f, Polynomial[][] table) {
        int maxDegree = f.getPoly().getDegree();
        int p = f.getP();
        Polynomial[] els = f.getElements();

        int pLength = 0;
        while (p != 0) {
            p /= 10;
            pLength++;
        }

        int width = maxDegree * (maxDegree + 2 + pLength) + pLength;

        System.out.println();
        for (int i = 0; i < table.length; i++) {
            for (int j = 0; j < table[i].length; j++) {
                String s = table[i][j].toString();
                int padSize = width - s.length();
                int padStart = s.length() + padSize / 2;

                s = String.format("%" + padStart + "s", s);
                s = String.format("%-" + width  + "s", s);

                System.out.print(s);
            }
            System.out.println();
        }
        System.out.println();
    }



}
