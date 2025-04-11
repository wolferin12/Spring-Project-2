import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Grapher {
    public static void main(String[] args) {
        List<double[]> data = new ArrayList<>();

        // Generate multiple function datasets
        for (double x = -15; x <= 15; x += 0.5) {
            double y1 = Math.pow(x, 3) + 3;  // Function: y = x^3 + 3
            double y2 = Math.pow(x, 4) + 10; // Function: y = x^4 + 10
            double y3 = Math.pow(x, 5) + 1;  // Function: y = x^5 + 1

            // Store the values in a list
            data.add(new double[]{x, y1, y2, y3});
        }

        // Export the generated data to a CSV file
        exportToCSV(data, "plotter.csv");
        System.out.println("Data has been exported!");
    }

    // Method to export dataset to a CSV file
    public static void exportToCSV(List<double[]> data, String filename) {
        try (FileWriter writer = new FileWriter(filename)) {
            // Write CSV headers for readability
            writer.append("x,y_x^3+3,y_x^4+10,y_x^5+1\n");

            // Iterate through the dataset and write values
            for (double[] point : data) {
                writer.append(point[0] + "," + point[1] + "," + point[2] + "," + point[3] + "\n");
            }
        } catch (IOException e) {
            // Handle file writing errors
            System.out.println("Error writing file: " + e.getMessage());
        }
    }
}