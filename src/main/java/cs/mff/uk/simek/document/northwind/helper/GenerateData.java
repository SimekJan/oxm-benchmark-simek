package cs.mff.uk.simek.document.northwind.helper;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.UpdateOneModel;
import com.mongodb.client.model.Updates;
import com.mongodb.client.model.WriteModel;
import cs.mff.uk.simek.BenchmarkDataGenerator;
import cs.mff.uk.simek.document.MongoDbManger;
import cs.mff.uk.simek.document.northwind.*;
import org.bson.types.ObjectId;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GenerateData {

    private static final int SUPPLIER_COUNT = 1_000;
    private static final int EMPLOYEE_COUNT = 2_000;
    private static final int PRODUCT_COUNT = 5_000;
    private static final int CUSTOMER_COUNT = 10_000;
    private static final int ORDER_COUNT = 50_000;

    private static final int BATCH_SIZE = 2_000;

    public static void main(String[] args) {

        MongoDatabase db = MongoDbManger.getDb();

        MongoCollection<Customer> customers = db.getCollection("Customers", Customer.class);
        MongoCollection<Product> products = db.getCollection("Products", Product.class);
        MongoCollection<Order> orders = db.getCollection("Orders", Order.class);
        MongoCollection<Employee> employees = db.getCollection("Employees", Employee.class);
        MongoCollection<Supplier> suppliers = db.getCollection("Suppliers", Supplier.class);

        BenchmarkDataGenerator gen = new BenchmarkDataGenerator();

        Map<Long, ObjectId> supplierIds = generateSuppliers(suppliers, gen);
        generateSupplierRelations(suppliers, gen, supplierIds);

        Map<Long, ObjectId> productIds = generateProducts(products, gen, supplierIds);

        Map<Long, ObjectId> employeeIds = generateEmployees(employees, gen);

        generateEmployeeHierarchy(gen, employees, employeeIds);

        Map<Long, ObjectId> customerIds = generateCustomers(customers, gen);

        generateOrders(orders, gen, customerIds, employeeIds, productIds);
    }

    private static Map<Long, ObjectId> generateSuppliers(MongoCollection<Supplier> suppliers, BenchmarkDataGenerator gen) {

        Map<Long, ObjectId> supplierObjectIds = new HashMap<>();
        List<Supplier> batch = new ArrayList<>();

        for (long i = 1; i <= SUPPLIER_COUNT; i++) {

            Supplier supplier = new Supplier(i, gen.nextCompanyName(), gen.nextCity());
            batch.add(supplier);

            if (batch.size() >= BATCH_SIZE) {
                suppliers.insertMany(batch);

                for (Supplier s : batch) {
                    supplierObjectIds.put(s.getSupplierId(), s.getId());
                }

                batch.clear();
            }
        }

        if (!batch.isEmpty()) {
            suppliers.insertMany(batch);
            for (Supplier s : batch) {
                supplierObjectIds.put(s.getSupplierId(), s.getId());
            }
        }

        return supplierObjectIds;
    }

    private static void generateSupplierRelations(MongoCollection<Supplier> suppliers, BenchmarkDataGenerator gen, Map<Long, ObjectId> supplierIds) {

        List<WriteModel<Supplier>> updates = new ArrayList<>();

        for (long i = 1; i <= SUPPLIER_COUNT; i++) {

            int depCount = gen.nextInt(2, 8);

            List<ObjectId> dependencies = new ArrayList<>();

            for (int j = 0; j < depCount; j++) {

                long depSupplierId = gen.nextInt(1, SUPPLIER_COUNT + 1);

                if (depSupplierId == i)
                    continue;

                ObjectId depObjectId = supplierIds.get(depSupplierId);

                if (!dependencies.contains(depObjectId))
                    dependencies.add(depObjectId);
            }

            updates.add(new UpdateOneModel<>(Filters.eq("supplierId", i), Updates.set("suppliedBy", dependencies)));

            if (updates.size() >= BATCH_SIZE) {
                suppliers.bulkWrite(updates);
                updates.clear();
            }
        }

        if (!updates.isEmpty())
            suppliers.bulkWrite(updates);
    }

    private static Map<Long, ObjectId> generateProducts(MongoCollection<Product> products,
                                                        BenchmarkDataGenerator gen,
                                                        Map<Long, ObjectId> supplierIds) {

        Map<Long, ObjectId> productIds = new HashMap<>();
        List<Product> batch = new ArrayList<>();

        for (long i = 1; i <= PRODUCT_COUNT; i++) {

            ObjectId supplier =
                    supplierIds.get((long) gen.nextInt(1, SUPPLIER_COUNT + 1));

            Product product = new Product(
                    i,
                    gen.nextProductName(),
                    gen.nextPrice(),
                    gen.nextCategory(),
                    supplier
            );

            batch.add(product);

            if (batch.size() >= BATCH_SIZE) {
                products.insertMany(batch);

                for (Product p : batch)
                    productIds.put(p.getProductId(), p.getId());

                batch.clear();
            }
        }

        if (!batch.isEmpty()) {
            products.insertMany(batch);

            for (Product p : batch)
                productIds.put(p.getProductId(), p.getId());
        }

        return productIds;
    }

    private static Map<Long, ObjectId> generateEmployees(MongoCollection<Employee> employees,
                                                         BenchmarkDataGenerator gen) {

        Map<Long, ObjectId> employeeIds = new HashMap<>();

        List<Employee> batch = new ArrayList<>();

        for (long i = 1; i <= EMPLOYEE_COUNT; i++) {

            Employee employee = new Employee(
                    i,
                    gen.nextFirstName(),
                    gen.nextLastName(),
                    gen.nextBirthDate(),
                    gen.nextHireDate(),
                    gen.nextCity()
            );

            batch.add(employee);

            if (batch.size() >= BATCH_SIZE) {
                employees.insertMany(batch);

                for (Employee e : batch)
                    employeeIds.put(e.getEmployeeId(), e.getId());

                batch.clear();
            }
        }

        if (!batch.isEmpty()) {

            employees.insertMany(batch);

            for (Employee e : batch)
                employeeIds.put(e.getEmployeeId(), e.getId());
        }

        return employeeIds;
    }

    private static void generateEmployeeHierarchy(
            BenchmarkDataGenerator gen,
            MongoCollection<Employee> employees,
            Map<Long, ObjectId> employeeIds) {

        List<WriteModel<Employee>> updates = new ArrayList<>();

        List<Long> potentialManagers = new ArrayList<>();
        potentialManagers.add(1L); // CEO

        int managerIndex = 0;
        int reportsAssigned = 0;
        int nextReportsToAssign = gen.nextInt(3,8);

        for (long employee = 2; employee <= EMPLOYEE_COUNT; employee++) {

            long manager = potentialManagers.get(managerIndex);

            updates.add(
                    new UpdateOneModel<>(
                            Filters.eq("employeeId", employee),
                            Updates.set("reportsTo", employeeIds.get(manager))
                    )
            );

            potentialManagers.add(employee);

            reportsAssigned++;

            if (reportsAssigned == nextReportsToAssign) {
                nextReportsToAssign = gen.nextInt(3,8);
                reportsAssigned = 0;
                managerIndex++;
            }

            if (updates.size() >= BATCH_SIZE) {
                employees.bulkWrite(updates);
                updates.clear();
            }
        }

        if (!updates.isEmpty()) {
            employees.bulkWrite(updates);
        }
    }

    private static Map<Long, ObjectId> generateCustomers(MongoCollection<Customer> customers,
                                                         BenchmarkDataGenerator gen) {

        Map<Long, ObjectId> customerIds = new HashMap<>();

        List<Customer> batch = new ArrayList<>();

        for (long i = 1; i <= CUSTOMER_COUNT; i++) {

            Customer customer = new Customer(
                    i,
                    gen.nextCompanyName(),
                    gen.nextCity()
            );

            batch.add(customer);

            if (batch.size() >= BATCH_SIZE) {

                customers.insertMany(batch);

                for (Customer c : batch)
                    customerIds.put(c.getCustomerId(), c.getId());

                batch.clear();
            }
        }

        if (!batch.isEmpty()) {

            customers.insertMany(batch);

            for (Customer c : batch)
                customerIds.put(c.getCustomerId(), c.getId());
        }

        return customerIds;
    }

    private static void generateOrders(MongoCollection<Order> orders,
                                       BenchmarkDataGenerator gen,
                                       Map<Long, ObjectId> customerIds,
                                       Map<Long, ObjectId> employeeIds,
                                       Map<Long, ObjectId> productIds) {

        List<Order> batch = new ArrayList<>();

        for (long i = 1; i <= ORDER_COUNT; i++) {

            ObjectId customer =
                    customerIds.get((long) gen.nextInt(1, CUSTOMER_COUNT + 1));

            ObjectId employee =
                    employeeIds.get((long) gen.nextInt(1, EMPLOYEE_COUNT + 1));

            int productCount = gen.nextInt(0, 8);

            List<ObjectId> orderedProducts = new ArrayList<>();

            while (orderedProducts.size() < productCount) {

                ObjectId product =
                        productIds.get((long) gen.nextInt(1, PRODUCT_COUNT + 1));

                if (!orderedProducts.contains(product))
                    orderedProducts.add(product);
            }

            Order order = new Order(
                    i,
                    customer,
                    employee,
                    gen.nextOrderDate(),
                    orderedProducts
            );

            batch.add(order);

            if (batch.size() >= BATCH_SIZE) {
                orders.insertMany(batch);
                batch.clear();
            }
        }

        if (!batch.isEmpty())
            orders.insertMany(batch);
    }
}
