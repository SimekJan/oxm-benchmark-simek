package cs.mff.uk.simek.importer;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import cs.mff.uk.simek.document.MongoDbManger;
import cs.mff.uk.simek.document.northwind.*;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Iterator;

import static com.mongodb.client.model.Indexes.ascending;

public class MongoImporter {

    private static MongoDatabase db;

    private static final int BATCH_SIZE = 2_000;

    /**
     * Loads all data from specified CSV path in config to chosen MongoDB instance.
     */
    public static void run() throws IOException {

        db = MongoDbManger.getDb();

        loadSuppliers();
        loadCustomers();
        loadEmployees();
        loadProducts();
        loadOrders();
    }

    private static void loadSuppliers() throws IOException {

        MongoCollection<Supplier> suppliers = db.getCollection("Suppliers", Supplier.class);
        CSVParser parser = CsvParser.getParser("suppliers.csv");
        CSVParser relationshipsParser = CsvParser.getParser("supplier_relationships.csv");
        Iterator<CSVRecord> relationships = relationshipsParser.iterator();
        CSVRecord relationship = relationships.hasNext() ? relationships.next() : null;
        List<Supplier> batch = new ArrayList<>();

        for (CSVRecord record : parser) {
            long supplierId = Long.parseLong(record.get("supplier_id"));

            Supplier supplier = new Supplier(
                supplierId,
                record.get("company_name"),
                record.get("city")
            );

            // Consume all relationships belonging to this supplier
            while (relationship != null) {
                long firstId = Long.parseLong(relationship.get("supplier_id"));
                long secondId = Long.parseLong(relationship.get("supplied_to_id"));

                if (firstId > supplierId) {
                    break;
                }

                supplier.getSuppliedBy().add(secondId);

                relationship = relationships.hasNext() ?
                        relationships.next() : null;
            }

            batch.add(supplier);

            if (batch.size() >= BATCH_SIZE) {
                suppliers.insertMany(batch);
                batch.clear();
            }
        }

        if(!batch.isEmpty()) {
            suppliers.insertMany(batch);
        }

        suppliers.createIndex(ascending("supplierId"));
    }

    private static void loadCustomers() throws IOException {

        MongoCollection<Customer> customers = db.getCollection("Customers", Customer.class);
        CSVParser parser = CsvParser.getParser("customers.csv");
        List<Customer> batch = new ArrayList<>();

        for (CSVRecord record : parser) {
            Customer customer = new Customer(
                Long.valueOf(record.get("customer_id")),
                record.get("company_name"),
                record.get("city")
            );

            batch.add(customer);

            if (batch.size() >= BATCH_SIZE) {
                customers.insertMany(batch);
                batch.clear();
            }
        }

        if(!batch.isEmpty()) {
            customers.insertMany(batch);
        }

        customers.createIndex(ascending("customerId"));
    }

    private static void loadEmployees() throws IOException {

        MongoCollection<Employee> employees = db.getCollection("Employees", Employee.class);
        CSVParser parser = CsvParser.getParser("employees.csv");
        List<Employee> batch = new ArrayList<>();

        for (CSVRecord record : parser) {
            Employee employee = new Employee(
                    Long.valueOf(record.get("employee_id")),
                    record.get("first_name"),
                    record.get("last_name"),
                    LocalDate.parse(record.get("birth_date")),
                    LocalDate.parse(record.get("hire_date")),
                    record.get("city")
            );

            String reportsTo = record.get("reports_to");

            employee.setReportsTo(reportsTo == null || reportsTo.isBlank() ?
                            null : Long.valueOf(reportsTo));

            batch.add(employee);

            if (batch.size() >= BATCH_SIZE) {
                employees.insertMany(batch);
                batch.clear();
            }
        }

        if(!batch.isEmpty()) {
            employees.insertMany(batch);
        }

        employees.createIndex(ascending("employeeId"));
    }

    private static void loadProducts() throws IOException {

        MongoCollection<Product> products = db.getCollection("Product", Product.class);
        CSVParser parser = CsvParser.getParser("products.csv");
        List<Product> batch = new ArrayList<>();

        for (CSVRecord record : parser) {
            Product product = new Product(
                    Long.valueOf(record.get("product_id")),
                    record.get("product_name"),
                    Float.valueOf(record.get("unit_price")),
                    record.get("category"),
                    Long.valueOf(record.get("supplier_id"))
            );

            batch.add(product);

            if (batch.size() >= BATCH_SIZE) {
                products.insertMany(batch);
                batch.clear();
            }
        }

        if(!batch.isEmpty()) {
            products.insertMany(batch);
        }

        products.createIndex(ascending("productId"));
    }

    private static void loadOrders() throws IOException {

        MongoCollection<Order> orders = db.getCollection("Orders", Order.class);
        CSVParser parser = CsvParser.getParser("orders.csv");
        CSVParser order_products = CsvParser.getParser("order_products.csv");
        Iterator<CSVRecord> relationships = order_products.iterator();
        CSVRecord relationship = relationships.hasNext() ? relationships.next() : null;
        List<Order> batch = new ArrayList<>();

        for (CSVRecord record : parser) {
            long orderId = Long.parseLong(record.get("order_id"));

            Order order = new Order(
                    orderId,
                    Long.parseLong(record.get("customer_id")),
                    Long.parseLong(record.get("employee_id")),
                    LocalDate.parse(record.get("order_date"))
                );

            // Consume all relationships belonging to this supplier
            while (relationship != null) {
                long relationshipOrderId = Long.parseLong(relationship.get("order_id"));
                long productId = Long.parseLong(relationship.get("product_id"));

                if (relationshipOrderId > orderId) {
                    break;
                }

                order.getProducts().add(productId);

                relationship = relationships.hasNext() ?
                        relationships.next() : null;
            }

            batch.add(order);

            if (batch.size() >= BATCH_SIZE) {
                orders.insertMany(batch);
                batch.clear();
            }
        }

        if(!batch.isEmpty()) {
            orders.insertMany(batch);
        }

        orders.createIndex(ascending("orderId"));
    }
}
