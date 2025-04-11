import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class Smooth {
    public static void main(String[] args) {
        String inputFile = "Salted.csv";  // Input file containing salted data
        String outputFile = "Smoothed.csv"; // Output file for smoothed data
        int window = 3; // Window size for smoothing

        // Read data from CSV
        List<double[]> data = readCSV(inputFile);

        if (data != null) {
            applySmoothing(data, window); // Apply smoothing algorithm
            exportCSV(data, outputFile);  // Export smoothed data
            System.out.println("Smoothed data has been exported!");
        }
    }

    // Reads a CSV file and stores data in a list
    public static List<double[]> readCSV(String filename) {
        List<double[]> data = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String line;
            reader.readLine(); // Skip header row

            while ((line = reader.readLine()) != null) {
                String[] values = line.split(",");
                if (values.length < 4) continue; // Ensure correct format

                try {
                    // Parse values into numerical format
                    double x = Double.parseDouble(values[0]);
                    double y1 = Double.parseDouble(values[1]);
                    double y2 = Double.parseDouble(values[2]);
                    double y3 = Double.parseDouble(values[3]);
                    data.add(new double[]{x, y1, y2, y3});
                } catch (NumberFormatException e) {
                    System.err.println("Skipping invalid row: " + line);
                }
            }
        } catch (IOException e) {
            System.err.println("Error reading CSV: " + e.getMessage());
        }
        return data;
    }

    // Applies moving average smoothing to all function values
    public static void applySmoothing(List<double[]> data, int window) {
        List<double[]> smoothedData = new ArrayList<>();

        for (int i = 0; i < data.size(); i++) {
            double[] point = data.get(i);
            // Calculate smoothed values for each function column
            double y1Smooth = calculateWindowAvg(data, i, window, 1);
            double y2Smooth = calculateWindowAvg(data, i, window, 2);
            double y3Smooth = calculateWindowAvg(data, i, window, 3);
            smoothedData.add(new double[]{point[0], y1Smooth, y2Smooth, y3Smooth});
        }

        // Replace original data with smoothed values
        data.clear();
        data.addAll(smoothedData);
    }

    // Computes moving average for a specific column index
    public static double calculateWindowAvg(List<double[]> data, int index, int window, int columnIndex) {
        int halfWin = window / 2;
        double sum = 0;
        int count = 0;

        // Iterate through the window range and calculate average
        for (int i = Math.max(0, index - halfWin); i <= Math.min(data.size() - 1, index + halfWin); i++) {
            sum += data.get(i)[columnIndex];
            count++;
        }
        return sum / count; // Return the computed average
    }

    // Exports smoothed data to a CSV file
    public static void exportCSV(List<double[]> data, String filename) {
        try (FileWriter writer = new FileWriter(filename)) {
            // Write headers for the CSV file
            writer.append("x,y_x^3+3_smoothed,y_x^4+10_smoothed,y_x^5+1_smoothed\n");

            // Write processed data into the CSV file
            for (double[] point : data) {
                writer.append(point[0] + "," + point[1] + "," + point[2] + "," + point[3] + "\n");
            }

            System.out.println("Smoothed CSV saved as " + filename + "!");
        } catch (IOException e) {
            System.err.println("Error writing file: " + e.getMessage());
        }
    }
}
