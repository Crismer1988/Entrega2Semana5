// Cristian Orlando Mercado Perilla
//Entrega 2 Semana 5
//Fundamentos de programacion - Subgrupo 5

import java.io.*;
import java.util.*;

public class GenerateInfoFiles {

    //GeneratedProducts needs to be initialized and ready to execute from here.
    private static final Set<String> generatedProducts = new HashSet<>();
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
                } while (generatedProducts.contains(productName)); // Check if the product has already been generated
                generatedProducts.add(productName); // Add the generated product to the registry

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
                String salesmanDocument = salesmanData[0] + "_" + salesmanData[1]; // Document type and number
                String salesmanSalesFileName = "SalesmanSales_" + salesmanDocument + ".txt"; // Individual file per seller

                try (PrintWriter writer = new PrintWriter(salesmanSalesFileName)) {
                    List<String> products = new ArrayList<>();
                    try (BufferedReader productReader = new BufferedReader(new FileReader("ProductInfo.txt"))) {
                        String productLine;
                        while ((productLine = productReader.readLine()) != null) {
                            String[] productData = productLine.split(";");
                            products.add(productData[0]); // Product name
                        }
                    }

                    Random random = new Random();
                    int salesCount = random.nextInt(10) + 5; // Between 5 and 15 sales per seller
                    for (int i = 0; i < salesCount; i++) {
                        String productSold = products.get(random.nextInt(products.size()));
                        int quantitySold = random.nextInt(10) + 1; // Between 1 and 10 units sold
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
        return Double.parseDouble(String.format("%.2f", price)); //Use only two decimal places
    }
}