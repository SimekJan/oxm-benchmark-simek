package cs.mff.uk.simek.graph.northwind.helper;

import cs.mff.uk.simek.graph.Neo4jSessionManager;
import cs.mff.uk.simek.graph.northwind.*;
import org.neo4j.ogm.session.Session;

import java.time.LocalDate;
import java.util.Collection;

public class PopulateWithDummyData {

    public static void main(String[] args) {

        Session session = Neo4jSessionManager.getSession();

        // ---------- SUPPLIERS ----------

        Supplier alpha = new Supplier(1L, "Alpha", "Prague");
        Supplier beta = new Supplier(2L, "Beta", "Brno");
        Supplier gamma = new Supplier(3L, "Gamma", "Ostrava");
        Supplier delta = new Supplier(4L, "Delta", "Plzen");
        Supplier epsilon = new Supplier(5L, "Epsilon", "Liberec");

// bidirectional supplier chain
        alpha.addSuppliesTo(beta);
        alpha.addSuppliesTo(gamma);

        beta.addSuppliesTo(delta);
        beta.addSuppliesTo(gamma);
        beta.addSuppliesTo(epsilon);

        gamma.addSuppliesTo(epsilon);
        gamma.addSuppliesTo(delta);

        delta.addSuppliesTo(gamma);


// ---------- PRODUCTS ----------

        Product p1 = new Product(1L, "Bolt", 10);
        Product p2 = new Product(2L, "Nut", 15);
        Product p3 = new Product(3L, "Screw", 9);
        Product p4 = new Product(4L, "Steel Plate", 12);
        Product p5 = new Product(5L, "Gear", 12);
        Product p6 = new Product(6L, "Valve", 16);

// connect BOTH sides
        alpha.addProduct(p1);
        alpha.addProduct(p2);

        beta.addProduct(p3);
        gamma.addProduct(p4);
        delta.addProduct(p5);
        epsilon.addProduct(p6);


// ---------- EMPLOYEES ----------

        Employee ceo = new Employee(1L, "Alice", "CEO", LocalDate.now(), "Karlovy Vary");
        Employee manager1 = new Employee(2L, "Bob", "Manager", LocalDate.now(), "Praha");
        Employee manager2 = new Employee(3L, "Carol", "Manager", LocalDate.now(), "Brno");
        Employee worker1 = new Employee(4L, "David", "Worker", LocalDate.now(), "Liberec");
        Employee worker2 = new Employee(5L, "Eva", "Worker", LocalDate.now(), "Ostrava");
        Employee worker3 = new Employee(6L, "Steve", "Worker", LocalDate.now(), "Liberec");

// hierarchy (both sides)
        ceo.addSubordinate(manager1);
        ceo.addSubordinate(manager2);

        manager1.addSubordinate(worker1);
        manager2.addSubordinate(worker2);
        manager2.addSubordinate(worker3);


// ---------- CUSTOMERS ----------

        Customer c1 = new Customer(1L, "A", "Karlovy Vary");
        Customer c2 = new Customer(2L, "B", "Praha");
        Customer c3 = new Customer(3L, "C", "Olomouc");
        Customer c4 = new Customer(4L, "D", "Ostrava");
        Customer c5 = new Customer(5L, "E", "Brno");


// ---------- ORDERS ----------

        Order o1 = new Order();
        Order o2 = new Order();
        Order o3 = new Order();
        Order o4 = new Order();
        Order o5 = new Order();
        Order o6 = new Order();

// customer ↔ order
        c1.addOrder(o1);
        c2.addOrder(o2);
        c3.addOrder(o3);
        c4.addOrder(o4);
        c5.addOrder(o5);
        c5.addOrder(o6);

// employee ↔ order
        manager1.addOrder(o1);
        manager1.addOrder(o2);

        manager2.addOrder(o3);

        worker1.addOrder(o4);
        worker1.addOrder(o6);

        worker2.addOrder(o5);

// order ↔ products
        o1.addProduct(p1);
        o1.addProduct(p3);

        o2.addProduct(p2);
        o2.addProduct(p4);

        o3.addProduct(p5);

        o4.addProduct(p6);
        o4.addProduct(p1);

        o5.addProduct(p2);
        o5.addProduct(p3);

// o6 empty intentionally


// ---------- SAVE ----------
        session.save(ceo);
        session.save(manager1);
        session.save(manager2);
        session.save(worker1);
        session.save(worker2);
        session.save(worker3);

        session.save(alpha);
        session.save(beta);
        session.save(gamma);
        session.save(delta);
        session.save(epsilon);

        session.save(c1);
        session.save(c2);
        session.save(c3);
        session.save(c4);
        session.save(c5);


        // ---------- VERIFY ----------

        Collection<Supplier> suppliers = session.loadAll(Supplier.class);
        suppliers.forEach(System.out::println);

        Collection<Employee> employees = session.loadAll(Employee.class);
        employees.forEach(System.out::println);

        Collection<Order> orders = session.loadAll(Order.class);
        orders.forEach(System.out::println);

        Collection<Product> products = session.loadAll(Product.class);
        products.forEach(System.out::println);

        Collection<Customer> customers = session.loadAll(Customer.class);
        customers.forEach(System.out::println);
    }
}
