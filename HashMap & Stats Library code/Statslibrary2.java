import java.util.Random;

public class Statslibrary2 {
    // Computes the Poisson Probability of a given value v with parameter lambda
    public static double getPoissonRandom(int v, double lambda) {
        // Poisson formula: P(X = v) = (lambda^v * e^-lambda) / v!
        return (Math.pow(lambda, v) + Math.exp(-lambda)) / factorial(v);
    }

    // Calculates the factorial of a number (used in Poisson distribution)
    private static int factorial(int z) {
        int result = 1;
        for (int i = 1; i <= z; i++) {
            result *= i; // Compute factorial iteratively
        }
        return result;
    }

    // Computes the mean (average) of a given dataset
    public static double mean(double[] data) {
        double sum = 0;
        for (double num : data) {
            sum += num; // Sum all elements in the dataset
        }
        return sum / data.length; // Divide sum by number of values
    }

    // Computes the standard deviation of a dataset using the mean
    public static double Deviation(double[] data, double mean) {
        double sum = 0;
        for (double val : data) {
            sum += Math.pow(val - mean, 2); // Sum squared differences from the mean
        }
        return Math.sqrt(sum / data.length); // Compute the square root of variance
    }

    // Applies Chebyshev's inequality to determine probability bounds
    public static void chebyshev(double[] data, double k) {
        double mean = mean(data); // Compute mean of dataset
        double dev = Deviation(data, mean); // Compute standard deviation

        // Chebyshev's bound formula: Probability >= 1 - (1/k^2)
        double bound = 1 - (1 / (k * k));
        int count = 0;

        // Count the values within k standard deviations of the mean
        for (double num : data) {
            if (Math.abs(num - mean) < k * dev) {
                count++;
            }
        }

        // Calculate actual probability from sample data
        double probability = (double) count / data.length;

        // Print Chebyshev's bound and actual probability observed
        System.out.println("Chebyshev lower bound: " + bound);
        System.out.println("Actual Percentage: " + k + " probability: " + probability);
    }

    public static void main(String[] args) {
        // Define Poisson distribution parameters
        double lambda = 1.5;
        int v = 2;

        // Compute and print Poisson probability
        double probability = getPoissonRandom(v, lambda);
        System.out.printf("Poisson Probability (P(X = %d)) = %.6f%n", v, probability);

        // Generate a random dataset for Chebyshev's inequality test
        int size = 10; // Number of data points
        double k = 2;  // Standard deviation multiplier

        Random random = new Random();
        double[] data = new double[size];

        // Generate 10 random values following a normal distribution (Gaussian)
        for (int i = 0; i < size; i++) {
            data[i] = random.nextGaussian() * 10 + 50; // Shifted normal distribution
        }

        // Apply Chebyshev’s inequality to the generated dataset
        chebyshev(data, k);
    }
}