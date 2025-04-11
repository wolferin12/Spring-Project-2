import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtils;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class JFreeGrapher{
    public static void main(String[] args) {
        XYSeries series1 = new XYSeries("y = x^3 + 3");
        XYSeries series2 = new XYSeries("y = x^4 + 10");
        XYSeries series3 = new XYSeries("y = x^5");

        // Create CSV file and dataset
        try (FileWriter writer = new FileWriter("data.csv");
             CSVPrinter csvPrinter = new CSVPrinter(writer, CSVFormat.DEFAULT.withHeader("x", "y_x^3+3", "y_x^4+10", "y_x^5+1"))) {

            for (double x = -15; x <= 15; x += 0.5) {
                double y1 = Math.pow(x, 3) + 3;
                double y2 = Math.pow(x, 4) + 10;
                double y3 = Math.pow(x, 5);

                // Add points to series
                series1.add(x, y1);
                series2.add(x, y2);
                series3.add(x, y3);

                // Write to CSV
                csvPrinter.printRecord(x, y1, y2, y3);
            }

            System.out.println("Data saved to data.csv!");

        } catch (IOException e) {
            System.err.println("Error writing CSV: " + e.getMessage());
            e.printStackTrace();
        }

        // Wrap series in a dataset
        XYSeriesCollection dataset = new XYSeriesCollection();
        dataset.addSeries(series1);
        dataset.addSeries(series2);
        dataset.addSeries(series3);

        // Create chart
        JFreeChart chart = ChartFactory.createXYLineChart(
                "Graphs of y = x^3 + 3, y = x^4 + 10, and y = x^5",
                "X-Axis",
                "Y-Axis",
                dataset,
                PlotOrientation.VERTICAL,
                true, true, false
        );

        // Save chart as PNG
        File file = new File("multi_graph.png");
        try {
            ChartUtils.saveChartAsPNG(file, chart, 800, 600);
            System.out.println("Graph saved as multi_graph.png!");
        } catch (IOException e) {
            System.err.println("Error saving chart: " + e.getMessage());
            e.printStackTrace();
        }
    }
}