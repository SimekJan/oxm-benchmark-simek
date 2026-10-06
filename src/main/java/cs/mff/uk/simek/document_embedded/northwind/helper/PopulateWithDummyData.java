package cs.mff.uk.simek.document_embedded.northwind.helper;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import cs.mff.uk.simek.document_embedded.EmbeddedMongoDbManager;
import cs.mff.uk.simek.document_embedded.northwind.*;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import static com.mongodb.client.model.Indexes.ascending;

public class PopulateWithDummyData {
    public static void main(String[] args) {

        MongoDatabase db = EmbeddedMongoDbManager.getDb();

        MongoCollection<Customer> customers = db.getCollection("Customers", Customer.class);
        MongoCollection<Product> products = db.getCollection("Products", Product.class);
        MongoCollection<Order> orders = db.getCollection("Orders", Order.class);
        MongoCollection<Employee> employees = db.getCollection("Employees", Employee.class);
        MongoCollection<Supplier> suppliers = db.getCollection("Suppliers", Supplier.class);

        // ---------- PRODUCTS ----------
        Product p1 = new Product(1L, "S-Bolt", 10F, "cat1");
        Product p2 = new Product(2L, "Nut", 15F, "cat2");
        Product p3 = new Product(3L, "Screw", 9F, "cat1");
        Product p4 = new Product(4L, "Steel Plate", 12F, "cat2");
        Product p5 = new Product(5L, "Gear", 12F, "cat2");
        Product p6 = new Product(6L, "Valve", 16F, "cat3");

        products.insertMany(List.of(p1, p2, p3, p4, p5, p6));
        products.createIndex(ascending("productId"));

        // ---------- SUPPLIERS ----------
        Supplier alpha = new Supplier(1L, "Alpha", "Prague", List.of(new ProductSnapshot(p1), new ProductSnapshot(p2)));
        Supplier beta = new Supplier(2L, "Beta", "Brno", List.of(new ProductSnapshot(p3)));
        Supplier gamma = new Supplier(3L, "Gamma", "Ostrava", List.of(new ProductSnapshot(p4)));
        Supplier delta = new Supplier(4L, "Delta", "Plzen", List.of(new ProductSnapshot(p5)));
        Supplier epsilon = new Supplier(5L, "Epsilon", "Liberec", List.of(new ProductSnapshot(p6)));

        alpha.setSuppliedBy(List.of());
        beta.setSuppliedBy(List.of(alpha.getId()));
        gamma.setSuppliedBy(List.of(alpha.getId(), beta.getId(), delta.getId()));
        delta.setSuppliedBy(List.of(beta.getId(), gamma.getId()));
        epsilon.setSuppliedBy(List.of(beta.getId(), gamma.getId()));

        suppliers.insertMany(Arrays.asList(alpha, beta, gamma, delta, epsilon));
        suppliers.createIndex(ascending("supplierId"));

        // ---------- ORDERS ----------
        Order o1 = new Order(1L, LocalDate.of(2025, 1, 1), Arrays.asList(p1.getId(), p3.getId()));
        Order o2 = new Order(2L, LocalDate.of(2025, 10, 11), Arrays.asList(p2.getId(), p4.getId()));
        Order o3 = new Order(3L, LocalDate.of(2025, 8, 21), List.of(p5.getId()));
        Order o4 = new Order(4L, LocalDate.of(2025, 9, 15), Arrays.asList(p6.getId(), p1.getId()));
        Order o5 = new Order(5L, LocalDate.of(2025, 7, 4), Arrays.asList(p2.getId(), p3.getId()));
        Order o6 = new Order(6L, LocalDate.of(2025, 12, 9), List.of());

        orders.insertMany(Arrays.asList(o1, o2, o3, o4, o5, o6));
        orders.createIndex(ascending("orderId"));

        // ---------- EMPLOYEES ----------
        Employee ceo = new Employee(1L, "Alice", "CEO", LocalDate.of(1985, 1, 1), LocalDate.of(2020, 3, 15), "Karlovy Vary");
        ceo.setOrders(List.of());
        employees.insertOne(ceo);

        Employee manager1 = new Employee(2L, "Bob", "Manager", LocalDate.of(1990, 1, 1), LocalDate.of(2020, 4, 15), "Prague");
        manager1.setOrders(List.of(new OrderSnapshot(o1), new OrderSnapshot(o2)));
        manager1.setReportsTo(ceo.getId());
        Employee manager2 = new Employee(3L, "Carol", "Manager", LocalDate.of(1992, 1, 1), LocalDate.of(2018, 3, 4), "Brno");
        manager2.setOrders(List.of(new OrderSnapshot(o3)));
        manager2.setReportsTo(ceo.getId());
        employees.insertMany(Arrays.asList(manager1, manager2));

        Employee worker1 = new Employee(4L, "David", "Worker", LocalDate.of(1994, 1, 1), LocalDate.of(2005, 7, 28), "Liberec");
        worker1.setOrders(List.of(new OrderSnapshot(o4), new OrderSnapshot(o6)));
        worker1.setReportsTo(manager1.getId());
        Employee worker2 = new Employee(5L, "Eva", "Worker", LocalDate.of(1995, 1, 1), LocalDate.of(2008, 4, 26), "Ostrava");
        worker2.setOrders(List.of(new OrderSnapshot(o5)));
        worker2.setReportsTo(manager2.getId());
        Employee worker3 = new Employee(6L, "Steve", "Worker", LocalDate.of(2001, 1, 1), LocalDate.of(2024, 1, 1), "Liberec");
        worker3.setOrders(List.of());
        worker3.setReportsTo(manager2.getId());

        employees.insertMany(Arrays.asList(worker1, worker2, worker3));
        employees.createIndex(ascending("employeeId"));

        // ---------- CUSTOMERS ----------
        Customer c1 = new Customer(1L, "A", "Karlovy Vary");
        c1.setOrders(List.of(new OrderSnapshot(o1)));
        Customer c2 = new Customer(2L, "B", "Prague");
        c2.setOrders(List.of(new OrderSnapshot(o2)));
        Customer c3 = new Customer(3L, "C", "Olomouc");
        c3.setOrders(List.of(new OrderSnapshot(o3)));
        Customer c4 = new Customer(4L, "D", "Ostrava");
        c4.setOrders(List.of(new OrderSnapshot(o4)));
        Customer c5 = new Customer(5L, "E", "Brno");
        c5.setOrders(List.of(new OrderSnapshot(o5), new OrderSnapshot(o6)));
        Customer c6 = new Customer(6L, "F", "Prague");
        c6.setOrders(List.of());

        customers.insertMany(Arrays.asList(c1, c2, c3, c4, c5, c6));
        customers.createIndex(ascending("customerId"));

        // ---------- ORDERS.REFERENCES ----------
        o1.setCustomer(c1.getId());
        o1.setEmployee(manager1.getId());
        orders.replaceOne(Filters.eq("orderId", o1.getOrderId()), o1);

        o2.setCustomer(c2.getId());
        o2.setEmployee(manager1.getId());
        orders.replaceOne(Filters.eq("orderId", o2.getOrderId()), o2);

        o3.setCustomer(c3.getId());
        o3.setEmployee(manager2.getId());
        orders.replaceOne(Filters.eq("orderId", o3.getOrderId()), o3);

        o4.setCustomer(c4.getId());
        o4.setEmployee(worker1.getId());
        orders.replaceOne(Filters.eq("orderId", o4.getOrderId()), o4);

        o5.setCustomer(c5.getId());
        o5.setEmployee(worker2.getId());
        orders.replaceOne(Filters.eq("orderId", o5.getOrderId()), o5);

        o6.setCustomer(c5.getId());
        o6.setEmployee(worker1.getId());
        orders.replaceOne(Filters.eq("orderId", o6.getOrderId()), o6);
    }
}
