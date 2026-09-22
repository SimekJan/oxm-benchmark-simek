package cs.mff.uk.simek.generator;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.time.LocalDate;
import java.util.ArrayDeque;
import java.util.HashSet;
import java.util.Queue;
import java.util.Set;

public class Generator {

    private static final int SUPPLIER_COUNT = 1_000;
    private static final int EMPLOYEE_COUNT = 2_000;
    private static final int PRODUCT_COUNT = 5_000;
    private static final int CUSTOMER_COUNT = 10_000;
    private static final int ORDER_COUNT = 20_000;

    private static final Path OUTPUT_DIR = Path.of(
            System.getenv().getOrDefault("OUTPUT_DIR", "generated")
    );
    private static final Path CSV_DIR = OUTPUT_DIR.resolve("csv");
    private static final Path JSON_DIR = OUTPUT_DIR.resolve("json");

    public static void main(String[] args) throws Exception {

        createDirectories();

        DataProvider gen = new DataProvider();

        generateSuppliers(gen);
        generateProducts(gen);
        generateEmployees(gen);
        generateCustomers(gen);
        generateOrders(gen);

        generateSupplierRelationships(gen);
        generateOrderProducts(gen);

        System.out.println("Data generation finished.");
        System.out.println("Output: " + OUTPUT_DIR.toAbsolutePath());
    }

    // -------------------------------------------------------------------------
    // Directories
    // -------------------------------------------------------------------------

    private static void createDirectories() throws IOException {
        Files.createDirectories(CSV_DIR);
        Files.createDirectories(JSON_DIR);
    }

    // -------------------------------------------------------------------------
    // Suppliers
    // -------------------------------------------------------------------------

    private static void generateSuppliers(DataProvider gen) throws IOException {

        Path csvPath = CSV_DIR.resolve("suppliers.csv");
        Path jsonPath = JSON_DIR.resolve("suppliers.json");

        try (
            BufferedWriter csvWriter = newWriter(csvPath);
            CSVPrinter csv = new CSVPrinter(
                csvWriter,
                CSVFormat.DEFAULT.builder()
                    .setHeader("supplier_id", "company_name", "city")
                    .get()
            );
            JsonGenerator json = jsonGenerator(jsonPath)
        ) {
            json.writeStartArray();

            for (long supplierId = 1; supplierId <= SUPPLIER_COUNT; supplierId++) {

                String companyName = gen.nextCompanyName();
                String city = gen.nextCity();

                csv.printRecord(supplierId, companyName, city);

                json.writeStartObject();
                json.writeNumberField("supplierId", supplierId);
                json.writeStringField("companyName", companyName);
                json.writeStringField("city", city);
                json.writeEndObject();
            }

            json.writeEndArray();
        }
    }

    // -------------------------------------------------------------------------
    // Products
    // -------------------------------------------------------------------------

    private static void generateProducts(DataProvider gen) throws IOException {

        Path csvPath = CSV_DIR.resolve("products.csv");
        Path jsonPath = JSON_DIR.resolve("products.json");

        try (
            BufferedWriter csvWriter = newWriter(csvPath);
            CSVPrinter csv = new CSVPrinter(
                csvWriter,
                CSVFormat.DEFAULT.builder()
                    .setHeader(
                        "product_id",
                        "product_name",
                        "unit_price",
                        "category",
                        "supplier_id"
                    )
                    .get()
            );
            JsonGenerator json = jsonGenerator(jsonPath)
        ) {
            json.writeStartArray();

            for (long productId = 1; productId <= PRODUCT_COUNT; productId++) {

                String productName = gen.nextProductName();
                double unitPrice = gen.nextPrice().doubleValue();
                String category = gen.nextCategory();

                // Pick an existing supplier ID directly.
                long supplierId = randomId(gen, SUPPLIER_COUNT);

                csv.printRecord(productId, productName, unitPrice, category, supplierId);

                json.writeStartObject();
                json.writeNumberField("productId", productId);
                json.writeStringField("productName", productName);
                json.writeNumberField("unitPrice", unitPrice);
                json.writeStringField("category", category);
                json.writeNumberField("supplierId", supplierId);
                json.writeEndObject();
            }

            json.writeEndArray();
        }
    }

    // -------------------------------------------------------------------------
    // Employees
    // -------------------------------------------------------------------------

    private static void generateEmployees(DataProvider gen) throws IOException {

        Path csvPath = CSV_DIR.resolve("employees.csv");
        Path jsonPath = JSON_DIR.resolve("employees.json");

        try (
            BufferedWriter csvWriter = newWriter(csvPath);
            CSVPrinter csv = new CSVPrinter(
                csvWriter,
                CSVFormat.DEFAULT.builder()
                    .setHeader(
                        "employee_id",
                        "first_name",
                        "last_name",
                        "birth_date",
                        "hire_date",
                        "city",
                        "reports_to"
                    )
                    .get()
            );
            JsonGenerator json = jsonGenerator(jsonPath)
        ) {
            json.writeStartArray();

            Queue<Long> managers = new ArrayDeque<>();

            // Employee 1 is the CEO.
            managers.add(1L);

            // Generate CEO.
            writeEmployee(1L, null, gen, csv, json);

            long employeeId = 2;

            while (employeeId <= EMPLOYEE_COUNT) {

                long managerId = managers.remove();
                int reports = gen.nextInt(2, 7);

                for (int r = 0; r < reports && employeeId <= EMPLOYEE_COUNT; r++) {

                    long currentEmployeeId = employeeId++;
                    writeEmployee(currentEmployeeId, managerId, gen, csv, json);

                    // This employee can itself become a manager.
                    managers.add(currentEmployeeId);
                }
            }

            json.writeEndArray();
        }
    }

    private static void writeEmployee(long employeeId, Long reportsTo, DataProvider gen,
                                      CSVPrinter csv, JsonGenerator json) throws IOException {

        String firstName = gen.nextFirstName();
        String lastName = gen.nextLastName();
        LocalDate birthDate = gen.nextBirthDate();
        LocalDate hireDate = gen.nextHireDate();
        String city = gen.nextCity();

        csv.printRecord( employeeId, firstName, lastName, birthDate, hireDate, city, reportsTo);

        json.writeStartObject();
        json.writeNumberField("employeeId", employeeId);
        json.writeStringField("firstName", firstName);
        json.writeStringField("lastName", lastName);

        json.writeStringField("birthDate", birthDate.toString());
        json.writeStringField("hireDate", hireDate.toString());
        json.writeStringField("city", city);

        if (reportsTo == null) {
            json.writeNullField("reportsTo");
        } else {
            json.writeNumberField("reportsTo", reportsTo);
        }

        json.writeEndObject();
    }

    // -------------------------------------------------------------------------
    // Customers
    // -------------------------------------------------------------------------

    private static void generateCustomers(DataProvider gen) throws IOException {

        Path csvPath = CSV_DIR.resolve("customers.csv");
        Path jsonPath = JSON_DIR.resolve("customers.json");

        try (
            BufferedWriter csvWriter = newWriter(csvPath);
            CSVPrinter csv = new CSVPrinter(
                csvWriter,
                CSVFormat.DEFAULT.builder()
                    .setHeader("customer_id", "company_name", "city")
                    .get()
            );
            JsonGenerator json = jsonGenerator(jsonPath)
        ) {
            json.writeStartArray();

            for (long customerId = 1;
                 customerId <= CUSTOMER_COUNT;
                 customerId++) {

                String companyName = gen.nextCompanyName();
                String city = gen.nextCity();

                csv.printRecord(customerId, companyName, city);

                json.writeStartObject();
                json.writeNumberField("customerId", customerId);
                json.writeStringField("companyName", companyName);
                json.writeStringField("city", city);
                json.writeEndObject();
            }

            json.writeEndArray();
        }
    }

    // -------------------------------------------------------------------------
    // Orders
    // -------------------------------------------------------------------------

    private static void generateOrders(DataProvider gen) throws IOException {

        Path csvPath = CSV_DIR.resolve("orders.csv");
        Path jsonPath = JSON_DIR.resolve("orders.json");

        try (
            BufferedWriter csvWriter = newWriter(csvPath);
            CSVPrinter csv = new CSVPrinter(
                csvWriter,
                CSVFormat.DEFAULT.builder()
                    .setHeader("order_id", "order_date", "customer_id", "employee_id")
                    .get()
            );
            JsonGenerator json = jsonGenerator(jsonPath)
        ) {
            json.writeStartArray();

            for (long orderId = 1; orderId <= ORDER_COUNT; orderId++) {

                LocalDate orderDate = gen.nextOrderDate();

                long customerId = randomId(gen, CUSTOMER_COUNT);
                long employeeId = randomId(gen, EMPLOYEE_COUNT);

                csv.printRecord(orderId, orderDate, customerId, employeeId);

                json.writeStartObject();
                json.writeNumberField("orderId", orderId);
                json.writeStringField("orderDate", orderDate.toString());
                json.writeNumberField("customerId", customerId);
                json.writeNumberField("employeeId", employeeId);
                json.writeEndObject();
            }

            json.writeEndArray();
        }
    }

    // -------------------------------------------------------------------------
    // Supplier -> Supplier
    // -------------------------------------------------------------------------

    private static void generateSupplierRelationships(DataProvider gen) throws IOException {

        Path csvPath = CSV_DIR.resolve("supplier_relationships.csv");
        Path jsonPath = JSON_DIR.resolve("supplier_relationships.json");

        try (
            BufferedWriter csvWriter = newWriter(csvPath);
            CSVPrinter csv = new CSVPrinter(
                csvWriter,
                CSVFormat.DEFAULT.builder()
                    .setHeader("supplier_id", "supplied_to_id")
                    .get()
            );
            JsonGenerator json = jsonGenerator(jsonPath)
        ) {
            json.writeStartArray();

            for (long supplierId = 1; supplierId <= SUPPLIER_COUNT; supplierId++) {

                int linkCount = gen.nextInt(1, 5);
                Set<Long> targets = new HashSet<>();

                while (targets.size() < linkCount) {
                    long targetId = randomId(gen, SUPPLIER_COUNT);
                    if (targetId != supplierId) {
                        targets.add(targetId);
                    }
                }

                for (long targetId : targets) {

                    csv.printRecord(supplierId, targetId);

                    json.writeStartObject();
                    json.writeNumberField("supplierId", supplierId);
                    json.writeNumberField("suppliedToId", targetId);
                    json.writeEndObject();
                }
            }

            json.writeEndArray();
        }
    }

    // -------------------------------------------------------------------------
    // Order -> Product
    // -------------------------------------------------------------------------

    private static void generateOrderProducts(DataProvider gen) throws IOException {

        Path csvPath = CSV_DIR.resolve("order_products.csv");
        Path jsonPath = JSON_DIR.resolve("order_products.json");

        try (
            BufferedWriter csvWriter = newWriter(csvPath);
            CSVPrinter csv = new CSVPrinter(
                csvWriter,
                CSVFormat.DEFAULT.builder()
                    .setHeader("order_id", "product_id")
                    .get()
            );
            JsonGenerator json = jsonGenerator(jsonPath)
        ) {
            json.writeStartArray();

            for (long orderId = 1; orderId <= ORDER_COUNT; orderId++) {

                int productCount = gen.nextInt(1, 5);
                Set<Long> products = new HashSet<>();

                while (products.size() < productCount) {
                    products.add(randomId(gen, PRODUCT_COUNT));
                }

                for (long productId : products) {

                    csv.printRecord(orderId, productId);

                    json.writeStartObject();
                    json.writeNumberField("orderId", orderId);
                    json.writeNumberField("productId", productId);
                    json.writeEndObject();
                }
            }

            json.writeEndArray();
        }
    }

    // -------------------------------------------------------------------------
    // Helpers
    // -------------------------------------------------------------------------

    private static long randomId(DataProvider gen, long count) {
        return gen.nextInt(1, (int) count + 1);
    }

    private static BufferedWriter newWriter(Path path) throws IOException {
        return Files.newBufferedWriter(
            path,
            StandardOpenOption.CREATE,
            StandardOpenOption.TRUNCATE_EXISTING,
            StandardOpenOption.WRITE
        );
    }

    private static JsonGenerator jsonGenerator(Path path) throws IOException {
        ObjectMapper mapper = new ObjectMapper();

        return mapper.getFactory().createGenerator(
            newWriter(path)
        );
    }
}