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

import static com.mongodb.client.model.Indexes.ascending;

public class GenerateData {

    private static final int SUPPLIER_COUNT = 1_000;
    private static final int PRODUCT_COUNT = 5_000;
    private static final int EMPLOYEE_COUNT = 2_000;
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

        Map<Long, ObjectId> supplierIdsMap = generateSuppliers(suppliers, gen);
        generateSupplierRelations(suppliers, gen, supplierIdsMap);
        // generateProducts(products, gen);
        // generateEmployees(employees, gen);
        // generateCustomers(customers, gen);
        // generateOrders(orders, gen);

        suppliers.createIndex(ascending("supplierId"));
        products.createIndex(ascending("productId"));
        employees.createIndex(ascending("employeeId"));
        customers.createIndex(ascending("customerId"));
        orders.createIndex(ascending("orderId"));
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

            int depCount = gen.nextInt(0, 4);

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

    // TODO: rest

}
