package cs.mff.uk.simek.importer;

import cs.mff.uk.simek.relational.northwind.*;
import cs.mff.uk.simek.relational.HibernateSessionManager;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.postgresql.PGConnection;
import org.postgresql.copy.CopyManager;

import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.sql.PreparedStatement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Slf4j
public class PostgresImporter {

    private static final int BATCH_SIZE = 2_000;

    private static Session session;

    public static void run() throws IOException {

        System.out.println(">>> ENTERED PostgresImporter.run()");

        session = HibernateSessionManager.getSession();

        System.out.println(">>> GOT SESSION");

        Transaction transaction = session.beginTransaction();

        System.out.println(">>> BEGAN TRANSACTION");

        try {
            System.out.println(">>> LOADING SUPPLIERS");
            loadSuppliers();
            System.out.println(">>> SUPPLIERS LOADED");

            System.out.println(">>> LINKING SUPPLIERS");
            linkSuppliers();
            System.out.println(">>> SUPPLIERS LINKED");

            System.out.println(">>> COMMITTING");
            transaction.commit();

            System.out.println(">>> COMMITTED");

        } catch (Exception e) {
            e.printStackTrace();

            if (transaction.isActive()) {
                transaction.rollback();
            }

            throw e;

        } finally {
            System.out.println(">>> CLOSING SESSION");
            session.close();
        }
    }

    private static void loadSuppliers() throws IOException {

        CSVParser parser = CsvParser.getParser("suppliers.csv");

        int count = 0;

        for (CSVRecord record : parser) {

            Supplier supplier = new Supplier(
                Long.parseLong(record.get("supplier_id")),
                record.get("company_name"),
                record.get("city")
            );

            session.persist(supplier);

            if (++count % BATCH_SIZE == 0) {
                session.flush();
                session.clear();
            }
        }

        session.flush();
        session.clear();
    }

    private static void linkSuppliers() throws IOException {

        CSVParser parser = CsvParser.getParser("supplier_relationships.csv");

        int count = 0;

        for (CSVRecord record : parser) {

            Long supplierId = Long.parseLong(record.get("supplier_id"));
            Long suppliedToId = Long.parseLong(record.get("supplied_to_id"));

            List<Supplier> suppliers = session.createQuery("""
                from Suppliers s
                where s.supplierId in (:supplierId, :suppliedToId)
                """, Supplier.class)
                    .setParameter("supplierId", supplierId)
                    .setParameter("suppliedToId", suppliedToId)
                    .getResultList();

            if (suppliers.size() != 2) {
                throw new IllegalStateException(
                    "Supplier not found: " +
                        supplierId + " or " + suppliedToId
                );
            }

            Supplier supplier = suppliers.stream()
                    .filter(s -> s.getSupplierId().equals(supplierId))
                    .findFirst()
                    .orElseThrow();

            Supplier suppliedTo = suppliers.stream()
                    .filter(s -> s.getSupplierId().equals(suppliedToId))
                    .findFirst()
                    .orElseThrow();

            supplier.addSuppliesTo(suppliedTo);

            if (++count % BATCH_SIZE == 0) {
                session.flush();
                session.clear();
            }
        }

        session.flush();
        session.clear();
    }
/*
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

            batch.add(employee);

            if (batch.size() >= BATCH_SIZE) {
                employees.insertMany(batch);
                batch.clear();
            }
        }

        if(!batch.isEmpty()) {
            employees.insertMany(batch);
        }
    }

    private static void linkEmployees() throws IOException {

        CSVParser parser = CsvParser.getParser("employees.csv");
        List<Map<String, Long>> batch = new ArrayList<>(BATCH_SIZE);

        for (CSVRecord record : parser) {

            String reportsTo = record.get("reports_to");

            // CEO / top-level employee
            if (reportsTo == null || reportsTo.isBlank()) {
                continue;
            }

            batch.add(Map.of(
                    "employeeId", Long.parseLong(record.get("employee_id")),
                    "reportsTo", Long.parseLong(reportsTo)
            ));

            if (batch.size() == BATCH_SIZE) {
                saveEmployeeRelationships(batch);
                batch.clear();
            }
        }

        if (!batch.isEmpty()) {
            saveEmployeeRelationships(batch);
        }
    }

    private static void saveEmployeeRelationships(
            List<Map<String, Long>> relationships) {

        MongoCollection<Employee> employees = db.getCollection("Employees", Employee.class);
        List<WriteModel<Employee>> operations = new ArrayList<>();

        for (Map<String, Long> relationship : relationships) {

            Employee reportsTo = employees.find(
                    Filters.eq("employeeId", relationship.get("reportsTo"))
            ).first();

            if (reportsTo == null) {
                throw new IllegalStateException(
                        "Employee not found: " + relationship.get("reportsTo")
                );
            }

            operations.add(
                    new UpdateOneModel<>(
                            Filters.eq("employeeId", relationship.get("employeeId")),
                            Updates.set("reportsTo", reportsTo.getId())
                    )
            );
        }

        if (!operations.isEmpty()) {
            employees.bulkWrite(
                    operations,
                    new BulkWriteOptions().ordered(false)
            );
        }
    }

    private static void loadProducts() throws IOException {

        MongoCollection<Product> products = db.getCollection("Products", Product.class);
        CSVParser parser = CsvParser.getParser("products.csv");
        List<Product> batch = new ArrayList<>();

        for (CSVRecord record : parser) {
            Product product = new Product(
                    Long.valueOf(record.get("product_id")),
                    record.get("product_name"),
                    Float.valueOf(record.get("unit_price")),
                    record.get("category")
            );

            batch.add(product);

            if (batch.size() >= BATCH_SIZE) {
                products.insertMany(batch);
                batch.clear();
            }
        }

        if (!batch.isEmpty()) {
            products.insertMany(batch);
        }
    }

    private static void linkProductsWithSuppliers() throws IOException {

        CSVParser parser = CsvParser.getParser("products.csv");

        List<Map<String, Long>> batch = new ArrayList<>(BATCH_SIZE);

        for (CSVRecord record : parser) {

            batch.add(Map.of(
                    "productId", Long.parseLong(record.get("product_id")),
                    "supplierId", Long.parseLong(record.get("supplier_id"))
            ));

            if (batch.size() == BATCH_SIZE) {
                saveProductSupplierRelationships(batch);
                batch.clear();
            }
        }

        if (!batch.isEmpty()) {
            saveProductSupplierRelationships(batch);
        }
    }

    private static void saveProductSupplierRelationships(
            List<Map<String, Long>> relationships) {

        MongoCollection<Product> products = db.getCollection("Products", Product.class);
        MongoCollection<Supplier> suppliers = db.getCollection("Suppliers", Supplier.class);
        List<WriteModel<Product>> operations = new ArrayList<>();

        for (Map<String, Long> relationship : relationships) {

            Supplier supplier = suppliers.find(
                    Filters.eq("supplierId", relationship.get("supplierId"))
            ).first();

            if (supplier == null) {
                throw new IllegalStateException(
                        "Supplier not found: " + relationship.get("supplierId")
                );
            }

            operations.add(
                    new UpdateOneModel<>(
                            Filters.eq("productId", relationship.get("productId")),
                            Updates.set("supplier", supplier.getId())
                    )
            );
        }

        if (!operations.isEmpty()) {
            products.bulkWrite(
                    operations,
                    new BulkWriteOptions().ordered(false)
            );
        }
    }

    private static void loadOrders() throws IOException {

        MongoCollection<Order> orders = db.getCollection("Orders", Order.class);
        CSVParser parser = CsvParser.getParser("orders.csv");
        List<Order> batch = new ArrayList<>();

        for (CSVRecord record : parser) {

            Order order = new Order(
                    Long.parseLong(record.get("order_id")),
                    LocalDate.parse(record.get("order_date"))
            );

            batch.add(order);

            if (batch.size() >= BATCH_SIZE) {
                orders.insertMany(batch);
                batch.clear();
            }
        }

        if(!batch.isEmpty()) {
            orders.insertMany(batch);
        }
    }

    private static void linkOrdersWithCustomers() throws IOException {

        CSVParser parser = CsvParser.getParser("orders.csv");
        List<Map<String, Long>> batch = new ArrayList<>(BATCH_SIZE);

        for (CSVRecord record : parser) {

            batch.add(Map.of(
                    "orderId", Long.parseLong(record.get("order_id")),
                    "customerId", Long.parseLong(record.get("customer_id"))
            ));

            if (batch.size() == BATCH_SIZE) {
                saveOrderCustomerRelationship(batch);
                batch.clear();
            }
        }

        if (!batch.isEmpty()) {
            saveOrderCustomerRelationship(batch);
        }
    }

    private static void saveOrderCustomerRelationship(
            List<Map<String, Long>> relationships) {

        MongoCollection<Order> orders = db.getCollection("Orders", Order.class);
        MongoCollection<Customer> customers = db.getCollection("Customers", Customer.class);
        List<WriteModel<Order>> operations = new ArrayList<>();

        for (Map<String, Long> relationship : relationships) {

            Customer customer = customers.find(
                    Filters.eq("customerId", relationship.get("customerId"))
            ).first();

            if (customer == null) {
                throw new IllegalStateException(
                        "Customer not found: " + relationship.get("customerId")
                );
            }

            operations.add(
                    new UpdateOneModel<>(
                            Filters.eq("orderId", relationship.get("orderId")),
                            Updates.set("customer", customer.getId())
                    )
            );
        }

        if (!operations.isEmpty()) {
            orders.bulkWrite(
                    operations,
                    new BulkWriteOptions().ordered(false)
            );
        }
    }

    private static void linkOrdersWithEmployees() throws IOException {

        CSVParser parser = CsvParser.getParser("orders.csv");

        List<Map<String, Long>> batch = new ArrayList<>(BATCH_SIZE);

        for (CSVRecord record : parser) {

            batch.add(Map.of(
                    "orderId", Long.parseLong(record.get("order_id")),
                    "employeeId", Long.parseLong(record.get("employee_id"))
            ));

            if (batch.size() == BATCH_SIZE) {
                saveOrderEmployeeRelationship(batch);
                batch.clear();
            }
        }

        if (!batch.isEmpty()) {
            saveOrderEmployeeRelationship(batch);
        }
    }

    private static void saveOrderEmployeeRelationship(
            List<Map<String, Long>> relationships) {

        MongoCollection<Order> orders = db.getCollection("Orders", Order.class);
        MongoCollection<Employee> employees = db.getCollection("Employees", Employee.class);
        List<WriteModel<Order>> operations = new ArrayList<>();

        for (Map<String, Long> relationship : relationships) {

            Employee employee = employees.find(
                    Filters.eq("employeeId", relationship.get("employeeId"))
            ).first();

            if (employee == null) {
                throw new IllegalStateException(
                        "Employee not found: " + relationship.get("employeeId")
                );
            }

            operations.add(
                    new UpdateOneModel<>(
                            Filters.eq("orderId", relationship.get("orderId")),
                            Updates.set("employee", employee.getId())
                    )
            );
        }

        if (!operations.isEmpty()) {
            orders.bulkWrite(
                    operations,
                    new BulkWriteOptions().ordered(false)
            );
        }
    }

    private static void linkOrdersWithProducts() throws IOException {

        CSVParser parser = CsvParser.getParser("order_products.csv");
        List<Map<String, Long>> batch = new ArrayList<>(BATCH_SIZE);

        for (CSVRecord record : parser) {

            batch.add(Map.of(
                    "orderId", Long.parseLong(record.get("order_id")),
                    "productId", Long.parseLong(record.get("product_id"))
            ));

            if (batch.size() == BATCH_SIZE) {
                saveOrderProductRelationships(batch);
                batch.clear();
            }
        }

        if (!batch.isEmpty()) {
            saveOrderProductRelationships(batch);
        }
    }

    private static void saveOrderProductRelationships(
            List<Map<String, Long>> relationships) {

        MongoCollection<Order> orders = db.getCollection("Orders", Order.class);
        MongoCollection<Product> products = db.getCollection("Products", Product.class);
        List<WriteModel<Order>> operations = new ArrayList<>();

        for (Map<String, Long> relationship : relationships) {

            Product product = products.find(
                    Filters.eq("productId", relationship.get("productId"))
            ).first();

            if (product == null) {
                throw new IllegalStateException(
                        "Product not found: " + relationship.get("productId")
                );
            }

            operations.add(
                    new UpdateOneModel<>(
                            Filters.eq("orderId", relationship.get("orderId")),
                            Updates.addToSet("products", product.getId())
                    )
            );
        }

        if (!operations.isEmpty()) {
            BulkWriteResult result = orders.bulkWrite(
                    operations,
                    new BulkWriteOptions().ordered(false)
            );

            System.out.println("Matched: " + result.getMatchedCount());
            System.out.println("Modified: " + result.getModifiedCount());
        }
    }*/
}
