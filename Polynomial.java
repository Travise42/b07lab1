public class Polynomial {
    public double[] coefficients;

    public Polynomial() {
        coefficients = new double[]{0};
    }

    public Polynomial(double[] coefficients) {
        if (coefficients == null || coefficients.length == 0) {
            this.coefficients = new double[]{0};
            return;
        }
        this.coefficients = coefficients.clone();
    }

    public Polynomial add(Polynomial poly) {
        final int SIZE = Math.max(this.coefficients.length, poly.coefficients.length);
        double[] new_coefficients = new double[SIZE];
        for (int i = 0; i < SIZE; i++)
            new_coefficients[i] = this.get_coefficient(i) + poly.get_coefficient(i);
        return new Polynomial(new_coefficients);
    }

    public double evaluate(double x) {
        double sum = 0;
        for (int power = 0; power < coefficients.length; power++)
            sum += coefficients[power] * Math.pow(x, power);
        return sum;
    }

    public boolean hasRoot(double x) {
        return evaluate(x) == 0;
    }

    /**
     * Return the ith coefficient of the polynomial.
     * Returns 0 if i < 0.
     */
    public double get_coefficient(int i) {
        if (0 <= i && i < coefficients.length)
            return coefficients[i];
        return 0;
    }
}