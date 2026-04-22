package cs.mff.uk.simek.document.northwind.helper;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import cs.mff.uk.simek.document.MongoDbManger;
import cs.mff.uk.simek.document.northwind.*;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import static com.mongodb.client.model.Indexes.ascending;

public class PopulateWithDummyData {

    public static void main(String[] args) {

        MongoDatabase db = MongoDbManger.getDb();

        MongoCollection<Customer> customers = db.getCollection("Customers", Customer.class);
        MongoCollection<Product> products = db.getCollection("Products", Product.class);
        MongoCollection<Order> orders = db.getCollection("Orders", Order.class);
        MongoCollection<Employee> employees = db.getCollection("Employees", Employee.class);
        MongoCollection<Supplier> suppliers = db.getCollection("Suppliers", Supplier.class);

        // ---------- SUPPLIERS ----------
        Supplier alpha = new Supplier(1L, "Alpha", "Praha");
        Supplier beta = new Supplier(2L, "Beta", "Brno");
        Supplier gamma = new Supplier(3L, "Gamma", "Ostrava");
        Supplier delta = new Supplier(4L, "Delta", "Plzen");
        Supplier epsilon = new Supplier(5L, "Epsilon", "Liberec");

        alpha.setSuppliedBy(List.of());
        beta.setSuppliedBy(List.of(alpha.getId()));
        gamma.setSuppliedBy(List.of(alpha.getId(), beta.getId(), delta.getId()));
        delta.setSuppliedBy(List.of(beta.getId(), gamma.getId()));
        epsilon.setSuppliedBy(List.of(beta.getId(), gamma.getId()));

        suppliers.insertMany(Arrays.asList(alpha, beta, gamma, delta, epsilon));
        suppliers.createIndex(ascending("supplierId"));

        // ---------- PRODUCTS ----------
        Product p1 = new Product(1L, "Bolt", 10F);
        p1.setSupplier(alpha.getId());
        Product p2 = new Product(2L, "Nut", 15F);
        p2.setSupplier(alpha.getId());
        Product p3 = new Product(3L, "Screw", 9F);
        p3.setSupplier(beta.getId());
        Product p4 = new Product(4L, "Steel Plate", 12F);
        p4.setSupplier(gamma.getId());
        Product p5 = new Product(5L, "Gear", 12F);
        p5.setSupplier(delta.getId());
        Product p6 = new Product(6L, "Valve", 16F);
        p6.setSupplier(epsilon.getId());

        products.insertMany(Arrays.asList(p1, p2, p3, p4, p5, p6));
        products.createIndex(ascending("productId"));

        // ---------- EMPLOYEES ----------
        Employee ceo = new Employee(1L, "Alice", "CEO", LocalDate.of(1985,1,1), LocalDate.of(2020,3,15), "Karlovy Vary");
        employees.insertOne(ceo);

        Employee manager1 = new Employee(2L, "Bob", "Manager", LocalDate.of(1990,1,1), LocalDate.of(2020,4,15), "Praha");
        manager1.setReportsTo(ceo.getId());
        Employee manager2 = new Employee(3L, "Carol", "Manager", LocalDate.of(1992,1,1), LocalDate.of(2018,3,4), "Brno");
        manager2.setReportsTo(ceo.getId());
        employees.insertMany(Arrays.asList(manager1, manager2));

        Employee worker1 = new Employee(4L, "David", "Worker", LocalDate.of(1994,1,1), LocalDate.of(2005,7,28), "Liberec");
        worker1.setReportsTo(manager1.getId());
        Employee worker2 = new Employee(5L, "Eva", "Worker", LocalDate.of(1995,1,1), LocalDate.of(2008,4,26), "Ostrava");
        worker2.setReportsTo(manager2.getId());
        Employee worker3 = new Employee(6L, "Steve", "Worker", LocalDate.of(2001,1,1), LocalDate.of(2024,1,1), "Liberec");
        worker3.setReportsTo(manager2.getId());
        employees.insertMany(Arrays.asList(worker1, worker2, worker3));

        // CREATE index over hire date -> hire date is indexed!!!
        // employees.createIndex(ascending("hireDate"));
        employees.createIndex(ascending("employeeId"));

        // ---------- CUSTOMERS ----------
        Customer c1 = new Customer(1L, "A", "Karlovy Vary");
        Customer c2 = new Customer(2L, "B", "Praha");
        Customer c3 = new Customer(3L, "C", "Olomouc");
        Customer c4 = new Customer(4L, "D", "Ostrava");
        Customer c5 = new Customer(5L, "E", "Brno");
        Customer c6 = new Customer(6L, "F", "Karlovy Vary");

        customers.insertMany(Arrays.asList(c1, c2, c3, c4, c5, c6));
        customers.createIndex(ascending("customerId"));

        // ---------- ORDERS ----------
        Order o1 = new Order(1L, c1.getId(), manager1.getId(), LocalDate.of(2025,1,1), Arrays.asList(p1.getId(), p3.getId()));
        Order o2 = new Order(2L, c2.getId(), manager1.getId(), LocalDate.of(2025,10,11),Arrays.asList(p2.getId(), p4.getId()));
        Order o3 = new Order(3L, c3.getId(), manager2.getId(), LocalDate.of(2025,8,21),List.of(p5.getId()));
        Order o4 = new Order(4L, c4.getId(), worker1.getId(), LocalDate.of(2025,9,15),Arrays.asList(p6.getId(), p1.getId()));
        Order o5 = new Order(5L, c5.getId(), worker2.getId(), LocalDate.of(2025,7,4),Arrays.asList(p2.getId(), p3.getId()));
        Order o6 = new Order(6L, c5.getId(), worker1.getId(), LocalDate.of(2025,12,9),List.of());

        orders.insertMany(Arrays.asList(o1, o2, o3, o4, o5, o6));
        orders.createIndex(ascending("orderId"));
    }
}
