// Cristian Orlando Mercado Perilla
//Entrega 2 Semana 5
//Fundamentos de programacion - Subgrupo 5

import java.io.*;
import java.util.*;

public class Main {
    public static void main(String[] args) {
        try {
            generateSalesReport();
            generateProductReport();
            System.out.println("Reports generated successfully.");
        } catch (IOException e) {
            System.err.println("Error generating reports: " + e.getMessage());
        }
    }

    public static void generateSalesReport() throws IOException {
        Map<String, Double> salesBySalesman = new HashMap<>();

        // Iterate over each seller's sales file
        File folder = new File(".");
        File[] files = folder.listFiles((dir, name) -> name.startsWith("SalesmanSales_") && name.endsWith(".txt"));

        for (File file : files) {
            try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    String[] parts = line.split(";");
                    String salesmanInfo = file.getName().replace("SalesmanSales_", "").replace(".txt", ""); // Obtener el tipo y número de documento del vendedor
                    double totalSales = salesBySalesman.getOrDefault(salesmanInfo, 0.0);
                    if (parts.length > 1) {
                        totalSales += Double.parseDouble(parts[1]); // Add sales for this seller
                    }
                    salesBySalesman.put(salesmanInfo, totalSales);
                }
            }
        }

        List<Map.Entry<String, Double>> sortedSales = new ArrayList<>(salesBySalesman.entrySet());
        sortedSales.sort(Map.Entry.comparingByValue(Comparator.reverseOrder()));

        try (PrintWriter writer = new PrintWriter("SalesReport.csv")) {
            for (Map.Entry<String, Double> entry : sortedSales) {
                writer.println(entry.getKey() + ";" + entry.getValue());
            }
        }
    }

    public static void generateProductReport() throws IOException {
        Map<String, Integer> salesByProduct = new HashMap<>();

        // Iterate over each seller's sales file
        File folder = new File(".");
        File[] files = folder.listFiles((dir, name) -> name.startsWith("SalesmanSales_") && name.endsWith(".txt"));

        for (File file : files) {
            try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    String[] parts = line.split(";");
                    if (parts.length > 1) {
                        String productName = parts[0];
                        int quantitySold = Integer.parseInt(parts[1]);
                        salesByProduct.put(productName, salesByProduct.getOrDefault(productName, 0) + quantitySold);
                    }
                }
            }
        }

        List<Map.Entry<String, Integer>> sortedProducts = new ArrayList<>(salesByProduct.entrySet());
        sortedProducts.sort(Map.Entry.comparingByValue(Comparator.reverseOrder()));

        try (PrintWriter writer = new PrintWriter("ProductReport.csv")) {
            for (Map.Entry<String, Integer> entry : sortedProducts) {
                writer.println(entry.getKey() + ";" + entry.getValue());
            }
        }
    }
}