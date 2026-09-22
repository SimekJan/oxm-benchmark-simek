package cs.mff.uk.simek.document_embedded.northwind.helper;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.UpdateOneModel;
import com.mongodb.client.model.Updates;
import com.mongodb.client.model.WriteModel;
import cs.mff.uk.simek.generator.DataProvider;
import cs.mff.uk.simek.document_embedded.MongoDbManger;
import cs.mff.uk.simek.document_embedded.northwind.*;
import org.bson.types.ObjectId;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.mongodb.client.model.Indexes.ascending;

public class GenerateData {

    private static final int SUPPLIER_COUNT = 1_000;
    private static final int EMPLOYEE_COUNT = 2_000;
    private static final int PRODUCT_COUNT = 5_000;
    private static final int CUSTOMER_COUNT = 10_000;
    private static final int ORDER_COUNT = 20_000;

    private static final int BATCH_SIZE = 2_000;

    public static void main(String[] args) {

        MongoDatabase db = MongoDbManger.getDb();

        MongoCollection<Customer> customers = db.getCollection("Customers", Customer.class);
        MongoCollection<Product> products = db.getCollection("Products", Product.class);
        MongoCollection<Order> orders = db.getCollection("Orders", Order.class);
        MongoCollection<Employee> employees = db.getCollection("Employees", Employee.class);
        MongoCollection<Supplier> suppliers = db.getCollection("Suppliers", Supplier.class);

        DataProvider gen = new DataProvider(1234567890L);

        Map<Long, Product> productMap = generateProducts(products, gen);
        Map<Long, Order> orderMap = generateOrders(orders, gen, productMap);

        Map<Long, ObjectId> supplierIds = generateSuppliers(suppliers, gen, productMap);

        generateSupplierRelations(suppliers, gen, supplierIds);

        Map<Long, ObjectId> employeeIds = generateEmployees(employees, gen, orderMap);

        generateEmployeeHierarchy(gen, employees, employeeIds);

        generateCustomers(customers, gen, orderMap);

        employees.createIndex(ascending("employeeId"));
        customers.createIndex(ascending("customerId"));
        products.createIndex(ascending("productId"));
        suppliers.createIndex(ascending("supplierId"));
        orders.createIndex(ascending("orderId"));
    }

    private static Map<Long, Product> generateProducts(MongoCollection<Product> products, DataProvider gen) {

        Map<Long, Product> map = new HashMap<>();
        List<Product> batch = new ArrayList<>();

        for (long i = 1; i <= PRODUCT_COUNT; i++) {

            Product p = new Product(i, gen.nextProductName(), gen.nextPrice(), gen.nextCategory());

            batch.add(p);
            map.put(i, p);

            if (batch.size() >= BATCH_SIZE) {
                products.insertMany(batch);
                batch.clear();
            }
        }

        if (!batch.isEmpty())
            products.insertMany(batch);

        return map;
    }

    private static Map<Long, Order> generateOrders(MongoCollection<Order> orders, DataProvider gen,
                                                   Map<Long, Product> productMap) {

        Map<Long, Order> map = new HashMap<>();
        List<Order> batch = new ArrayList<>();

        for (long i = 1; i <= ORDER_COUNT; i++) {

            int productCount = gen.nextInt(0, 8);
            List<ObjectId> productsList = new ArrayList<>();

            for (int j = 0; j < productCount; j++) {
                long pid = gen.nextInt(1, PRODUCT_COUNT + 1);
                productsList.add(productMap.get(pid).getId());
            }

            Order o = new Order(i, gen.nextOrderDate(), productsList);

            batch.add(o);
            map.put(i, o);

            if (batch.size() >= BATCH_SIZE) {
                orders.insertMany(batch);
                batch.clear();
            }
        }

        if (!batch.isEmpty())
            orders.insertMany(batch);

        return map;
    }

    private static Map<Long, ObjectId> generateSuppliers(
            MongoCollection<Supplier> suppliers,
            DataProvider gen,
            Map<Long, Product> productMap) {

        Map<Long, ObjectId> ids = new HashMap<>();
        List<Supplier> batch = new ArrayList<>();

        for (long i = 1; i <= SUPPLIER_COUNT; i++) {

            int productCount = gen.nextInt(1, 6);
            List<ProductSnapshot> snaps = new ArrayList<>();

            for (int j = 0; j < productCount; j++) {
                long pid = gen.nextInt(1, PRODUCT_COUNT + 1);
                snaps.add(new ProductSnapshot(productMap.get(pid)));
            }

            Supplier s = new Supplier(i, gen.nextCompanyName(), gen.nextCity(), snaps);

            batch.add(s);

            if (batch.size() >= BATCH_SIZE) {
                suppliers.insertMany(batch);

                for (Supplier sp : batch)
                    ids.put(sp.getSupplierId(), sp.getId());

                batch.clear();
            }
        }

        if (!batch.isEmpty()) {
            suppliers.insertMany(batch);

            for (Supplier sp : batch)
                ids.put(sp.getSupplierId(), sp.getId());
        }

        return ids;
    }


    private static void generateSupplierRelations(MongoCollection<Supplier> suppliers, DataProvider gen,
                                                  Map<Long, ObjectId> supplierIds) {

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

    private static Map<Long, ObjectId> generateEmployees(MongoCollection<Employee> employees,
                                                         DataProvider gen, Map<Long, Order> orderMap) {

        Map<Long, ObjectId> ids = new HashMap<>();
        List<Employee> batch = new ArrayList<>();

        for (long i = 1; i <= EMPLOYEE_COUNT; i++) {

            int orderCount = gen.nextInt(0, 5);
            List<OrderSnapshot> snaps = new ArrayList<>();

            for (int j = 0; j < orderCount; j++) {
                long oid = gen.nextInt(1, ORDER_COUNT + 1);
                snaps.add(new OrderSnapshot(orderMap.get(oid)));
            }

            Employee e = new Employee(
                    i,
                    gen.nextFirstName(),
                    gen.nextLastName(),
                    gen.nextBirthDate(),
                    gen.nextHireDate(),
                    gen.nextCity()
            );

            e.setOrders(snaps);

            batch.add(e);

            if (batch.size() >= BATCH_SIZE) {
                employees.insertMany(batch);

                for (Employee em : batch)
                    ids.put(em.getEmployeeId(), em.getId());

                batch.clear();
            }
        }

        if (!batch.isEmpty()) {
            employees.insertMany(batch);

            for (Employee em : batch)
                ids.put(em.getEmployeeId(), em.getId());
        }

        return ids;
    }

    private static void generateEmployeeHierarchy(
            DataProvider gen,
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

    private static void generateCustomers(
            MongoCollection<Customer> customers,
            DataProvider gen,
            Map<Long, Order> orderMap) {

        List<Customer> batch = new ArrayList<>();

        for (long i = 1; i <= CUSTOMER_COUNT; i++) {

            int orderCount = gen.nextInt(0, 5);
            List<OrderSnapshot> snaps = new ArrayList<>();

            for (int j = 0; j < orderCount; j++) {
                long oid = gen.nextInt(1, ORDER_COUNT + 1);
                snaps.add(new OrderSnapshot(orderMap.get(oid)));
            }

            Customer c = new Customer(i, gen.nextCompanyName(), gen.nextCity());

            c.setOrders(snaps);
            batch.add(c);

            if (batch.size() >= BATCH_SIZE) {
                customers.insertMany(batch);
                batch.clear();
            }
        }

        if (!batch.isEmpty())
            customers.insertMany(batch);
    }
}
