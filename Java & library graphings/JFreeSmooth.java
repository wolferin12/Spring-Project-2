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

public class JFreeSmooth {
    public static void main(String[] args) {
        String inputFile = "JSaltedGraph.csv";  // Use advanced salted dataset
        String outputFile = "JSmoothed.csv"; // Smoothed dataset
        int window = 3; // Smoothing window size

        List<double[]> data = readCSV(inputFile);

        if (data != null) {
            applySmoothing(data, window);
            exportToCSV(data, outputFile);
            graphSmoothedData(data);
            System.out.println("Smoothed graph and CSV saved!");
        }
    }

    public static List<double[]> readCSV(String file) {
        List<double[]> data = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            reader.readLine(); // Skip header

            while ((line = reader.readLine()) != null) {
                String[] values = line.split(",");
                if (values.length < 4) continue; // Ensure valid structure

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

    public static void applySmoothing(List<double[]> data, int window) {
        List<double[]> smoothedData = new ArrayList<>();

        for (int i = 0; i < data.size(); i++) {
            double[] point = data.get(i);
            double y1Smooth = calculateWindowAvg(data, i, window, 1);
            double y2Smooth = calculateWindowAvg(data, i, window, 2);
            double y3Smooth = calculateWindowAvg(data, i, window, 3);
            smoothedData.add(new double[]{point[0], y1Smooth, y2Smooth, y3Smooth});
        }

        data.clear();
        data.addAll(smoothedData);
    }

    public static double calculateWindowAvg(List<double[]> data, int index, int window, int columnIndex) {
        int halfWin = window / 2;
        double sum = 0;
        int count = 0;

        for (int i = Math.max(0, index - halfWin); i <= Math.min(data.size() - 1, index + halfWin); i++) {
            sum += data.get(i)[columnIndex];
            count++;
        }
        return sum / count;
    }

    public static void exportToCSV(List<double[]> data, String filename) {
        try (FileWriter writer = new FileWriter(filename);
             CSVPrinter csvPrinter = new CSVPrinter(writer, CSVFormat.DEFAULT.withHeader("x", "y_x^3+3_smoothed", "y_x^4+10_smoothed", "y_x^5+1_smoothed"))) {

            for (double[] point : data) {
                csvPrinter.printRecord(point[0], point[1], point[2], point[3]);
            }

            System.out.println("Smoothed CSV saved as " + filename + "!");
        } catch (IOException e) {
            System.err.println("Error writing file: " + e.getMessage());
        }
    }

    public static void graphSmoothedData(List<double[]> data) {
        XYSeries series1 = new XYSeries("Smoothed y = x^3 + 3");
        XYSeries series2 = new XYSeries("Smoothed y = x^4 + 10");
        XYSeries series3 = new XYSeries("Smoothed y = x^5 + 1");

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
                "Smoothed Graphs",
                "X-Axis",
                "Y-Axis",
                dataset,
                org.jfree.chart.plot.PlotOrientation.VERTICAL,
                true, true, false
        );

        // Save chart as PNG
        File file = new File("smoothed_graph.png");
        try {
            ChartUtils.saveChartAsPNG(file, chart, 800, 600);
            System.out.println("Graph saved as smoothed_graph.png!");
        } catch (IOException e) {
            System.err.println("Error saving chart: " + e.getMessage());
        }
    }
}