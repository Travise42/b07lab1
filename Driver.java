import java.io.File;

public class Driver {
    public static void main(String [] args) {
        
        Polynomial p = new Polynomial();
        System.out.println(p.evaluate(3));

        double[] c1a = {6, 5};
        int[] c1b = {0, 3};
        Polynomial p1 = new Polynomial(c1a, c1b);
        double[] c2a = {-2, -9};
        int[] c2b = {1, 4};
        Polynomial p2 = new Polynomial(c2a, c2b);
        System.out.println("p1 = " + p1);
        System.out.println("p2 = " + p2);

        Polynomial s1 = p1.add(p2);
        System.out.println("s1 = " + s1);
        System.out.println("s1(0.1) = " + s1.evaluate(0.1));

        Polynomial s2 = p1.multiply(p2);
        System.out.println("s2 = " + s2);
        System.out.println("s2(0.1) = " + s2.evaluate(0.1));
        
        if(s1.hasRoot(1))
            System.out.println("1 is a root of s1");
        else
            System.out.println("1 is not a root of s1");
        
        if(s2.hasRoot(0))
            System.out.println("0 is a root of s2");
        else
            System.out.println("0 is not a root of s2");

        File f = new File("poly_file.txt");
        Polynomial p3 = new Polynomial(f);
        System.out.println(p3);
        System.out.println("p3(0.1) = " + p3.evaluate(0.1));

        s2.saveToFile("poly_file2.txt");
    }
}