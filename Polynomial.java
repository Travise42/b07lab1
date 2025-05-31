import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class Polynomial {
    public double[] coefficients;
    public int[] exponents;

    public Polynomial() {
        coefficients = new double[0];
        exponents = new int[0];
    }

    public Polynomial(double[] coefficients, int[] exponents) {
        // Handle null parameters
        if (coefficients == null || exponents == null) {
            this.coefficients = new double[0];
            this.exponents = new int[0];
            return;
        }

        // Copy over values
        this.coefficients = coefficients;
        this.exponents = exponents;
        
        // Remove terms with 0 as their coefficient
        filter();
    }

    public Polynomial(File file) {
        try {
            // Retrieve polynomial from file
            Scanner s = new Scanner(file);
            String poly = s.next();
            s.close();

            // Seperate polynomial into terms
            poly = poly.replace("-", "+-");
            String[] terms = poly.split("\\+");

            // Initialize arrays for values
            coefficients = new double[terms.length];
            exponents = new int[terms.length];

            // Decode each term
            for (int i = 0; i < terms.length; i++) {

                // Seperate coefficients and exponents
                String[] elements = terms[i].split("x");
                if (elements.length == 0) continue;

                // Store coefficients and exponents
                coefficients[i] = Double.parseDouble(elements[0]);
                if (elements.length > 1) exponents[i] = Integer.parseInt(elements[1]);
                else exponents[i] = 0;
            }

            // Remove terms with 0 as their coefficient
            filter();
        }
        catch (FileNotFoundException e) {
            System.out.println("File does not exist. Could not initialize Polynomial.");
        }
    }

    public Polynomial add(Polynomial poly) {

        // Get length of new polynomial exponents array using pointers incrementing in accending order to handle duplicates
        int length = 0, this_iterator = 0, poly_iterator = 0;
        for (int temp_iterator = this_iterator; this_iterator < this.exponents.length && poly_iterator < poly.exponents.length; length++) {
            if (this.exponents[temp_iterator] <= poly.exponents[poly_iterator]) this_iterator++;
            if (this.exponents[temp_iterator] >= poly.exponents[poly_iterator]) poly_iterator++;
        }
        length += this.exponents.length + poly.exponents.length - this_iterator - poly_iterator;

        // Initialize arrays to store values
        double[] new_coefficients = new double[length];
        int[] new_exponents = new int[length];
        
        // Initialize/ reset pointers
        this_iterator = 0;
        poly_iterator = 0;
        int index = 0;

        // Store values into arrays using pointers
        while (this_iterator < this.exponents.length || poly_iterator < poly.exponents.length) {

            // If smaller, store this value and move this pointer
            int temp_iterator = this_iterator;
            if (poly_iterator >= poly.exponents.length || temp_iterator < this.exponents.length && this.exponents[temp_iterator] <= poly.exponents[poly_iterator]) {
                new_coefficients[index] += this.coefficients[temp_iterator];
                new_exponents[index] = this.exponents[temp_iterator];
                this_iterator++;
            }

            // If smaller, store poly value and move poly pointer
            if (temp_iterator >= this.exponents.length || poly_iterator < poly.exponents.length && this.exponents[temp_iterator] >= poly.exponents[poly_iterator]) {
                new_coefficients[index] += poly.coefficients[poly_iterator];
                new_exponents[index] = poly.exponents[poly_iterator];
                poly_iterator++;
            }

            index++;
        }

        return new Polynomial(new_coefficients, new_exponents);
    }

    public Polynomial multiply(Polynomial poly) {
        
        // Expand out each of the terms and add to new_poly
        Polynomial new_poly = new Polynomial();
        for (int i = 0; i < poly.exponents.length; i++) new_poly = new_poly.add(multByTerm(poly.coefficients[i], poly.exponents[i]));
        
        // Remove terms that cancelled out (coefficients of 0)
        new_poly.filter();
        return new_poly;
    }

    public double evaluate(double x) {
        
        // Equate the values by multiplying coefficients by x raised to the exponents
        double sum = 0;
        for (int i = 0; i < exponents.length; i++) sum += coefficients[i] * Math.pow(x, exponents[i]);
        return sum;
    }

    public boolean hasRoot(double x) {
        return evaluate(x) == 0;
    }

    @Override
    public String toString() {
        
        // No exponents means the polynomial is 0
        if (exponents.length == 0) {
            return "0.0";
        }

        // Create polynomial string by iterating through each term
        String string = "";
        for (int i = 0; i < exponents.length; i++) {

            // Seperate non-negative terms by '+'
            if (i > 0 && coefficients[i] > 0) string += "+";

            // Append the term to string, only including x if the exponent is not 0 and only including the exponent if its 2 or more
            if (coefficients[i] != 1) string += String.valueOf(coefficients[i]);
            if (exponents[i] > 0) string += "x";
            if (exponents[i] > 1) string += String.valueOf(exponents[i]);
        }

        return string;
    }

    public void saveToFile(String file) {
        try {
            FileWriter output = new FileWriter(file, false);

            // Save polynomial string to file
            output.write(this.toString());

            output.close();
        } catch (IOException e) {
            System.out.println("File does not exists. Could not save Polynomial.");
        }

    }

    private Polynomial multByTerm(double coefficient, int exponent) {

        // Create copy to by multiplied and returned
        Polynomial new_poly = new Polynomial(this.coefficients, this.exponents);

        // Multiply each term by the given coefficient and exponent
        for (int i = 0; i < new_poly.exponents.length; i++) {
            new_poly.coefficients[i] *= coefficient;
            new_poly.exponents[i] += exponent;
        }

        return new_poly;
    }

    private void filter() {

        // Get length of new polynomial arrays
        int length = 0;
        for (int i = 0; i < coefficients.length; i++) if (coefficients[i] != 0) length++;
        
        // Initialize arrays to store values
        double[] new_coefficients = new double[length];
        int[] new_exponents = new int[length];

        // Iterate through coefficients and skip coefficients of 0 as they are redundent
        for (int i = 0, index = 0; i < coefficients.length; i++) {
            if (coefficients[i] == 0) continue;

            // Copy over non-zero values
            new_coefficients[index] = coefficients[i];
            new_exponents[index] = exponents[i];
            index++;
        }

        // Store zero-free values
        coefficients = new_coefficients;
        exponents = new_exponents;
    }
}