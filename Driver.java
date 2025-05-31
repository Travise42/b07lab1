import java.io.File;

public class Driver {

    private static void out(Object output) {
        System.out.println(output);
    }
    public static void main(String [] args) {

        // Tests for emply polynomial initializaton
        Polynomial p = new Polynomial();

        out("\n-------------------------------");
        out("Test #1:");
        out("\nExpected:");
        out("0.0");
        out("Result:");
        out(p);

        out("\n-------------------------------");
        out("Test #2:");
        out("\nExpected:");
        out("0.0");
        out("Result:");
        out(p.evaluate(3));

        // Test combining empty polynomials
        p.add(new Polynomial());

        out("\n-------------------------------");
        out("Test #3:");
        out("\nExpected:");
        out("0.0");
        out("Result:");
        out(p);

        // Test initializing using arrays
        double[] c1 = new double[]{4.2, -3.1, 0, 5.7, 1, 2};
        int[] e1 = new int[]{0, 2, 3, 6, 8, 9};
        Polynomial p1 = new Polynomial(c1, e1);

        double[] c2 = new double[]{3, -2.1, 4.7};
        int[] e2 = new int[]{1, 2, 4};
        Polynomial p2 = new Polynomial(c2, e2);

        out("\n-------------------------------");
        out("Test #4:");
        out("\nExpected:");
        out("4.2-3.1x2+5.7x6+x8+2.0x9");
        out("Result:");
        out(p1);

        out("\n-------------------------------");
        out("Test #5:");
        out("\nExpected:");
        out("3.0x-2.1x2+4.7x4");
        out("Result:");
        out(p2);

        // Test addition of polynomials
        p = p1.add(p2);

        out("\n-------------------------------");
        out("Test #6:");
        out("\nExpected:");
        out("4.2-3.1x2+5.7x6+x8+2.0x9");
        out("3.0x-2.1x2+4.7x4");
        out("4.2+3.0x-5.2x2+4.7x4+5.7x6+x8+2.0x9");
        out("Result:");
        out(p1);
        out(p2);
        out(p);

        // Test evaluation

        out("\n-------------------------------");
        out("Test #7:");
        out("\nExpected:");
        out("4.2");
        out("24.217672");
        out("-1136.9819");
        out("50429.4");
        out("4.5998884");
        out("Result:");
        out(p.evaluate(0));
        out(p.evaluate(-1.4));
        out(p.evaluate(-2.2));
        out(p.evaluate(3));
        out(p.evaluate(0.2));

        // Test multiplying polynomials
        p = p1.multiply(p2);
        
        out("\n-------------------------------");
        out("Test #8:");
        out("\nExpected:");
        out("12.6x-8.82x2-9.3x3+26.25x4-14.57x6+17.1x7-11.97x8+3.0x9+30.69x10-4.2x11+4.7x12+9.4x13");
        out("Result:");
        out(p);

        out("\n-------------------------------");
        out("Test #9:");
        out("\nExpected:");
        out("-53.527342");
        out("0.0");
        out("Result:");
        out(p.evaluate(-1.6));
        out(p.evaluate(0));

        // Test multiplying by 0
        p = p1.multiply(new Polynomial());

        out("\n-------------------------------");
        out("Test #10:");
        out("\nExpected:");
        out("4.2-3.1x2+5.7x6+x8+2.0x9");
        out("0");
        out("Result:");
        out(p1);
        out(p);

        // Test loading from a file

        File f = new File("poly_file.txt");
        p1 = new Polynomial(f);

        out("\n-------------------------------");
        out("Test #11:");
        out("\nExpected:");
        out("5.1-3.2x2+7.0x8");
        out("Result:");
        out(p1);

        out("\n-------------------------------");
        out("Test #12:");
        out("\nExpected:");
        out("1784.3");
        out("Result:");
        out(p1.evaluate(2));

        out("\n-------------------------------");

        // Test saving to file

        p2.saveToFile("poly_file2.txt");
        // Should have 3.0x-2.1x2+4.7x4
    }
}