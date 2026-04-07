package cs.mff.uk.simek.document_embedded.northwind.helper;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import cs.mff.uk.simek.document.MongoDbManger;
import cs.mff.uk.simek.document_embedded.northwind.Order;
import cs.mff.uk.simek.document_embedded.northwind.Employee;
import cs.mff.uk.simek.document_embedded.northwind.Product;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import static com.mongodb.client.model.Indexes.ascending;

public class PopulateWithDummyData {
    public static void main(String[] args) {

        MongoDatabase db = MongoDbManger.getDb();

        MongoCollection<Order> orders = db.getCollection("Orders", Order.class);
        MongoCollection<Employee> employees = db.getCollection("Employees", Employee.class);

        // ---------- PRODUCTS ----------
        Product p1 = new Product("Bolt", 10);
        Product p2 = new Product("Nut", 15);
        Product p3 = new Product("Screw", 9);
        Product p4 = new Product("Steel Plate", 12);
        Product p5 = new Product("Gear", 12);
        Product p6 = new Product("Valve", 16);

        // ---------- EMPLOYEES ----------
        Employee ceo = new Employee("Alice", "CEO", LocalDate.of(1985,1,1), LocalDate.of(2020,3,15), "Karlovy Vary");
        employees.insertOne(ceo);

        // ---------- ORDERS ----------
        Order o1 = new Order(null, List.of(p1, p3));
        Order o2 = new Order(null, List.of(p2, p4));
        Order o3 = new Order(null, List.of(p5));
        Order o4 = new Order(null, List.of(p1, p6));
        Order o5 = new Order(null, List.of(p2, p3));
        Order o6 = new Order(null, List.of());

        Employee manager1 = new Employee("Bob", "Manager", LocalDate.of(1990,1,1), LocalDate.of(2020,4,15), "Praha");
        manager1.setReportsTo(ceo.getId());
        manager1.setOrders(List.of(o1, o2));
        Employee manager2 = new Employee("Carol", "Manager", LocalDate.of(1992,1,1), LocalDate.of(2018,3,4), "Brno");
        manager2.setReportsTo(ceo.getId());
        manager2.setOrders(List.of(o3));
        employees.insertMany(Arrays.asList(manager1, manager2));

        Employee worker1 = new Employee("David", "Worker", LocalDate.of(1994,1,1), LocalDate.of(2005,7,28), "Liberec");
        worker1.setReportsTo(manager1.getId());
        worker1.setOrders(List.of(o4, o6));
        Employee worker2 = new Employee("Eva", "Worker", LocalDate.of(1995,1,1), LocalDate.of(2008,4,26), "Ostrava");
        worker2.setReportsTo(manager2.getId());
        worker2.setOrders(List.of(o5));
        Employee worker3 = new Employee("Steve", "Worker", LocalDate.of(2001,1,1), LocalDate.of(2024,1,1), "Liberec");
        worker3.setReportsTo(manager2.getId());
        employees.insertMany(Arrays.asList(worker1, worker2, worker3));

        // CREATE index over hire date -> hire date is indexed!!!
        employees.createIndex(ascending("hireDate"));
    }
}
