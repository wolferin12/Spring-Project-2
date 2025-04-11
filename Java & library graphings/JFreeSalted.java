import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtils;
import org.jfree.chart.JFreeChart;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class JFreeSalted {
    public static void main(String[] args) {
        String inputFile = "Salted.csv";  // Original dataset
        String outputFile = "JSaltedGraph.csv";  // New dataset after advanced salting
        List<double[]> data = readCSV(inputFile);

        if (data != null) {
            applyAdvancedSalting(data);
            exportToCSV(data, outputFile);
            graphSaltedData(data);
            System.out.println("salted graph saved!");
        }
    }

    public static List<double[]> readCSV(String file) {
        List<double[]> data = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            reader.readLine(); // Skip header

            while ((line = reader.readLine()) != null) {
                String[] values = line.split(",");
                if (values.length < 4) continue; // Ensure proper format

                try {
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

    public static void applyAdvancedSalting(List<double[]> data) {
        Random random = new Random();

        for (double[] row : data) {
            for (int i = 1; i < row.length; i++) { // Skip x-value
                double noise = random.nextGaussian() * 10; // Gaussian noise
                double scale = (random.nextDouble() * 0.2) + 0.9; // Scale factor between 0.9x - 1.1x

                row[i] = row[i] * scale + noise;
            }
        }
    }

    public static void exportToCSV(List<double[]> data, String filename) {
        try (FileWriter writer = new FileWriter(filename);
             CSVPrinter csvPrinter = new CSVPrinter(writer, CSVFormat.DEFAULT.withHeader("x", "y_x^3+3", "y_x^4+10", "y_x^5+1"))) {

            for (double[] point : data) {
                csvPrinter.printRecord(point[0], point[1], point[2], point[3]);
            }

            System.out.println("Modified CSV saved as " + filename + "!");
        } catch (IOException e) {
            System.err.println("Error writing file: " + e.getMessage());
        }
    }

    public static void graphSaltedData(List<double[]> data) {
        XYSeries series1 = new XYSeries("Salted y = x^3 + 3");
        XYSeries series2 = new XYSeries("Salted y = x^4 + 10");
        XYSeries series3 = new XYSeries("Salted y = x^5 + 1");

        for (double[] point : data) {
            series1.add(point[0], point[1]);
            series2.add(point[0], point[2]);
            series3.add(point[0], point[3]);
        }

        // Wrap series in a dataset
        XYSeriesCollection dataset = new XYSeriesCollection();
        dataset.addSeries(series1);
        dataset.addSeries(series2);
        dataset.addSeries(series3);

        // Create chart
        JFreeChart chart = ChartFactory.createXYLineChart(
                "Advanced Salted Graphs",
                "X-Axis",
                "Y-Axis",
                dataset,
                org.jfree.chart.plot.PlotOrientation.VERTICAL,
                true, true, false
        );

        // Save chart as PNG
        File file = new File("salted_graph.png");
        try {
            ChartUtils.saveChartAsPNG(file, chart, 800, 600);
            System.out.println("Graph saved as salted_graph.png!");
        } catch (IOException e) {
            System.err.println("Error saving chart: " + e.getMessage());
        }
    }
}

