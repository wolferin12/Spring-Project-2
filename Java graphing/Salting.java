import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class Salting {
    public static void main(String[] args) {
        String inputFile = "plotter.csv"; // Input CSV containing function data
        String outputFile = "Salted.csv"; // Output file for salted data

        // Read data from the CSV file
        List<double[]> data = readCSV(inputFile);

        if (data != null) {
            applySalting(data, 1, 50, 25); // Salt values within range
            exportToCSV(data, outputFile); // Save modified dataset
            System.out.println("Salted data has been exported!");
        }
    }

    // Reads CSV and stores data in a list of arrays
    public static List<double[]> readCSV(String file) {
        List<double[]> data = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            reader.readLine(); // Skip header row

            while ((line = reader.readLine()) != null) {
                String[] values = line.split(",");
                if (values.length < 4) continue; // Ensure the row has enough data

                try {
                    // Parse values and store in array
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

    // Applies salting (adjusts function values within range)
    public static void applySalting(List<double[]> data, double minY, double maxY, double salt) {
        for (double[] row : data) {
            for (int i = 1; i < row.length; i++) { // Skip x-value
                if (row[i] >= minY && row[i] <= maxY) {
                    row[i] += salt; // Modify values within the specified range
                }
            }
        }
    }

    // Exports modified data to a CSV file
    public static void exportToCSV(List<double[]> data, String filename) {
        try (FileWriter writer = new FileWriter(filename)) {
            // Write headers for the CSV file
            writer.append("x,y_x^3+3_salted,y_x^4+10_salted,y_x^5+1_salted\n");

            // Write processed data into the CSV file
            for (double[] point : data) {
                writer.append(point[0] + "," + point[1] + "," + point[2] + "," + point[3] + "\n");
            }

            System.out.println("Salted CSV saved as " + filename + "!");
        } catch (IOException e) {
            System.err.println("Error writing file: " + e.getMessage());
        }
    }
}

