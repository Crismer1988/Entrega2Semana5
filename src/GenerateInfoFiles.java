// Cristian Orlando Mercado Perilla
//Entrega 2 Semana 5
//Fundamentos de programacion - Subgrupo 5

import java.io.*;
import java.util.*;

public class GenerateInfoFiles {

    //GeneratedProducts needs to be initailized and ready to execute from here.
    private static Set<String> generatedProducts = new HashSet<>();
    public static void main(String[] args) {
        try {
            createSalesManInfoFile(10);
            createProductsFile(10);
            createSalesmanSalesFile();
            System.out.println("Information files generated successfully.");
        } catch (IOException e) {
            System.err.println("Error generating information files: " + e.getMessage());
        }
    }

    public static void createSalesManInfoFile(int salesmanCount) throws IOException {
        try (PrintWriter writer = new PrintWriter("SalesmanInfo.txt")) {
            Random random = new Random();
            String[] documentTypes = {"CC", "TI", "CE"};
            for (int i = 1; i <= salesmanCount; i++) {
                String documentType = documentTypes[random.nextInt(documentTypes.length)];
                long documentNumber = generateRandomDocumentNumber();
                String firstName = generateRandomFirstName();
                String lastName = generateRandomLastName();
                writer.println(documentType + ";" + documentNumber + ";" + firstName + ";" + lastName);
            }
        }
    }

    public static void createProductsFile(int productsCount) throws IOException {
        try (PrintWriter writer = new PrintWriter("ProductInfo.txt")) {
            for (int i = 1; i <= productsCount; i++) {
                String productName;
                do {
                    productName = generateRandomProductName();
                } while (generatedProducts.contains(productName)); // Verificar si el producto ya fue generado
                generatedProducts.add(productName); // Agregar el producto generado al registro

                int productID = generateRandomProductID();
                double productPrice = generateRandomProductPrice();
                writer.println(productID + ";" + productName + ";" + productPrice);
            }
        }
    }

    public static void createSalesmanSalesFile() throws IOException {
        try (BufferedReader salesmanReader = new BufferedReader(new FileReader("SalesmanInfo.txt"))) {
            String salesmanLine;
            while ((salesmanLine = salesmanReader.readLine()) != null) {
                String[] salesmanData = salesmanLine.split(";");
                String salesmanDocument = salesmanData[0] + "_" + salesmanData[1]; // Tipo y número de documento
                String salesmanSalesFileName = "SalesmanSales_" + salesmanDocument + ".txt";

                try (PrintWriter writer = new PrintWriter(salesmanSalesFileName)) {
                    List<String> products = new ArrayList<>();
                    try (BufferedReader productReader = new BufferedReader(new FileReader("ProductInfo.txt"))) {
                        String productLine;
                        while ((productLine = productReader.readLine()) != null) {
                            String[] productData = productLine.split(";");
                            products.add(productData[0]); // Nombre del producto
                        }
                    }

                    Random random = new Random();
                    int salesCount = random.nextInt(10) + 5; // Entre 5 y 15 ventas por vendedor
                    for (int i = 0; i < salesCount; i++) {
                        String productSold = products.get(random.nextInt(products.size()));
                        int quantitySold = random.nextInt(10) + 1; // Entre 1 y 10 unidades vendidas
                        writer.println(productSold + ";" + quantitySold);
                    }
                }
            }
        }
    }

    // Auxiliary methods for generating random data:

    private static long generateRandomDocumentNumber() {
        Random random = new Random();
        return 1000000000L + random.nextInt(900000000);
    }

    private static int generateRandomProductID() {
        Random random = new Random();
        return random.nextInt(900000) + 100000;
    }

    private static String generateRandomFirstName() {
        String[] firstNames = {"John", "Emma", "Michael", "Sophia", "William", "Olivia", "James", "Ava", "Alexander", "Mia"};
        Random random = new Random();
        return firstNames[random.nextInt(firstNames.length)];
    }

    private static String generateRandomLastName() {
        String[] lastNames = {"Smith", "Johnson", "Brown", "Lee", "Wilson", "Taylor", "Clark", "Lewis", "Walker", "Moore"};
        Random random = new Random();
        return lastNames[random.nextInt(lastNames.length)];
    }

    private static String generateRandomProductName() {
        String[] productNames = {"Milk", "Bread", "Eggs", "Apples", "Bananas", "Rice", "Chicken", "Cheese", "Tomatoes", "Potatoes"};
        Random random = new Random();
        return productNames[random.nextInt(productNames.length)];
    }

    private static double generateRandomProductPrice() {
        Random random = new Random();
        double price = (random.nextDouble() * 50) + 1; // Generate random price between 1 and 50
        return Double.parseDouble(String.format("%.2f", price));
    }
}