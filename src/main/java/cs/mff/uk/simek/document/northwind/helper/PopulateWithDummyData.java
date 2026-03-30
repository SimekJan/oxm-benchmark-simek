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
        Supplier alpha = new Supplier("Alpha", "Praha");
        Supplier beta = new Supplier("Beta", "Brno");
        Supplier gamma = new Supplier("Gamma", "Ostrava");
        Supplier delta = new Supplier("Delta", "Plzen");
        Supplier epsilon = new Supplier("Epsilon", "Liberec");

        alpha.setSuppliedBy(List.of());
        beta.setSuppliedBy(List.of(alpha.getId()));
        gamma.setSuppliedBy(List.of(alpha.getId(), beta.getId(), delta.getId()));
        delta.setSuppliedBy(List.of(beta.getId(), gamma.getId()));
        epsilon.setSuppliedBy(List.of(beta.getId(), gamma.getId()));

        suppliers.insertMany(Arrays.asList(alpha, beta, gamma, delta, epsilon));

        // ---------- PRODUCTS ----------
        Product p1 = new Product("Bolt", 10);
        p1.setSupplier(alpha.getId());
        Product p2 = new Product("Nut", 15);
        p2.setSupplier(alpha.getId());
        Product p3 = new Product("Screw", 9);
        p3.setSupplier(beta.getId());
        Product p4 = new Product("Steel Plate", 12);
        p4.setSupplier(gamma.getId());
        Product p5 = new Product("Gear", 12);
        p5.setSupplier(delta.getId());
        Product p6 = new Product("Valve", 16);
        p6.setSupplier(epsilon.getId());

        products.insertMany(Arrays.asList(p1, p2, p3, p4, p5, p6));

        // ---------- EMPLOYEES ----------
        Employee ceo = new Employee("Alice", "CEO", LocalDate.of(1985,1,1), LocalDate.of(2020,3,15), "Karlovy Vary");
        employees.insertOne(ceo);

        Employee manager1 = new Employee("Bob", "Manager", LocalDate.of(1990,1,1), LocalDate.of(2020,4,15), "Praha");
        manager1.setReportsTo(ceo.getId());
        Employee manager2 = new Employee("Carol", "Manager", LocalDate.of(1992,1,1), LocalDate.of(2018,3,4), "Brno");
        manager2.setReportsTo(ceo.getId());
        employees.insertMany(Arrays.asList(manager1, manager2));

        Employee worker1 = new Employee("David", "Worker", LocalDate.of(1994,1,1), LocalDate.of(2005,7,28), "Liberec");
        worker1.setReportsTo(manager1.getId());
        Employee worker2 = new Employee("Eva", "Worker", LocalDate.of(1995,1,1), LocalDate.of(2008,4,26), "Ostrava");
        worker2.setReportsTo(manager2.getId());
        Employee worker3 = new Employee("Steve", "Worker", LocalDate.of(2001,1,1), LocalDate.of(2024,1,1), "Liberec");
        worker3.setReportsTo(manager2.getId());
        employees.insertMany(Arrays.asList(worker1, worker2, worker3));

        // CREATE index over hire date -> hire date is indexed!!!
        employees.createIndex(ascending("hireDate"));

        // ---------- CUSTOMERS ----------
        Customer c1 = new Customer("A", "Karlovy Vary");
        Customer c2 = new Customer("B", "Praha");
        Customer c3 = new Customer("C", "Olomouc");
        Customer c4 = new Customer("D", "Ostrava");
        Customer c5 = new Customer("E", "Brno");
        Customer c6 = new Customer("F", "Karlovy Vary");

        customers.insertMany(Arrays.asList(c1, c2, c3, c4, c5, c6));

        // ---------- ORDERS ----------
        Order o1 = new Order(c1.getId(), manager1.getId(), Arrays.asList(p1.getId(), p3.getId()));
        Order o2 = new Order(c2.getId(), manager1.getId(), Arrays.asList(p2.getId(), p4.getId()));
        Order o3 = new Order(c3.getId(), manager2.getId(), List.of(p5.getId()));
        Order o4 = new Order(c4.getId(), worker1.getId(), Arrays.asList(p6.getId(), p1.getId()));
        Order o5 = new Order(c5.getId(), worker2.getId(), Arrays.asList(p2.getId(), p3.getId()));
        Order o6 = new Order(c5.getId(), worker1.getId(), List.of());

        orders.insertMany(Arrays.asList(o1, o2, o3, o4, o5, o6));
    }
}
