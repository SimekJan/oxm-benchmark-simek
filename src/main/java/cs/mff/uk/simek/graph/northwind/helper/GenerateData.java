package cs.mff.uk.simek.graph.northwind.helper;

import cs.mff.uk.simek.BenchmarkDataGenerator;
import cs.mff.uk.simek.graph.Neo4jSessionManager;
import org.neo4j.ogm.session.Session;
import cs.mff.uk.simek.graph.northwind.*;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class GenerateData {
/*
    private static final int SUPPLIER_COUNT = 1_000;
    private static final int EMPLOYEE_COUNT = 2_000;
    private static final int PRODUCT_COUNT = 5_000;
    private static final int CUSTOMER_COUNT = 10_000;
    private static final int ORDER_COUNT = 50_000;
*/
    private static final int SUPPLIER_COUNT = 1_0;
    private static final int EMPLOYEE_COUNT = 2_0;
    private static final int PRODUCT_COUNT = 5_0;
    private static final int CUSTOMER_COUNT = 10_0;
    private static final int ORDER_COUNT = 50_0;

    public static void main(String[] args) {

        Session session = Neo4jSessionManager.getSession();
        BenchmarkDataGenerator gen = new BenchmarkDataGenerator();

        List<Supplier> suppliers = generateSuppliers(gen);
        List<Product> products = generateProducts(gen, suppliers);
        List<Employee> employees = generateEmployees(gen);
        List<Customer> customers = generateCustomers(gen);
        List<Order> orders = generateOrders(gen);

        linkSupplierProducts(suppliers, products, gen);
        generateEmployeeHierarchy(employees, gen);
        linkOrders(orders, customers, employees, products, gen);

        // SAVE (bulk-friendly in Neo4j OGM)
        suppliers.forEach(session::save);
        products.forEach(session::save);
        employees.forEach(session::save);
        customers.forEach(session::save);
        orders.forEach(session::save);
    }

    private static List<Supplier> generateSuppliers(BenchmarkDataGenerator gen) {

        List<Supplier> list = new ArrayList<>();

        for (long i = 1; i <= SUPPLIER_COUNT; i++) {
            list.add(new Supplier(i, gen.nextCompanyName(), gen.nextCity()));
        }

        return list;
    }

    private static void linkSupplierProducts(List<Supplier> suppliers, List<Product> products,
                                             BenchmarkDataGenerator gen) {

        for (Supplier s : suppliers) {

            int links = gen.nextInt(1, 5);

            for (int i = 0; i < links; i++) {
                Supplier target = suppliers.get(gen.nextInt(0, suppliers.size()));
                if (target != s) {
                    s.addSuppliesTo(target);
                }
            }
        }
    }

    private static List<Product> generateProducts(
            BenchmarkDataGenerator gen,
            List<Supplier> suppliers) {

        List<Product> products = new ArrayList<>();

        for (long i = 1; i <= PRODUCT_COUNT; i++) {

            Product p = new Product(
                    i,
                    gen.nextProductName(),
                    gen.nextPrice().doubleValue(),
                    gen.nextCategory()
            );

            Supplier s = suppliers.get(gen.nextInt(0, suppliers.size()));
            s.addProduct(p);

            products.add(p);
        }

        return products;
    }

    private static List<Employee> generateEmployees(BenchmarkDataGenerator gen) {

        List<Employee> employees = new ArrayList<>();

        for (long i = 1; i <= EMPLOYEE_COUNT; i++) {
            employees.add(new Employee(
                    i, gen.nextFirstName(), gen.nextLastName(), gen.nextBirthDate(),
                    gen.nextHireDate(), gen.nextCity()
            ));
        }

        return employees;
    }

    private static void generateEmployeeHierarchy(List<Employee> employees,
                                                  BenchmarkDataGenerator gen) {

        int maxReports = gen.nextInt(3, 8);

        Queue<Employee> managers = new LinkedList<>();
        managers.add(employees.getFirst()); // CEO

        int index = 1;

        while (index < employees.size()) {

            Employee manager = managers.poll();
            int reports = gen.nextInt(1, maxReports);

            for (int i = 0; i < reports && index < employees.size(); i++) {

                Employee e = employees.get(index++);
                manager.addSubordinate(e);

                managers.add(e); // becomes potential manager
            }
        }
    }

    private static List<Customer> generateCustomers(BenchmarkDataGenerator gen) {

        List<Customer> customers = new ArrayList<>();

        for (long i = 1; i <= CUSTOMER_COUNT; i++) {
            customers.add(new Customer(i, gen.nextCompanyName(), gen.nextCity()));
        }

        return customers;
    }

    private static List<Order> generateOrders(BenchmarkDataGenerator gen) {

        List<Order> orders = new ArrayList<>();

        for (long i = 1; i <= ORDER_COUNT; i++) {
            orders.add(new Order(
                    i,
                    gen.nextOrderDate()
            ));
        }

        return orders;
    }

    private static void linkOrders(List<Order> orders, List<Customer> customers, List<Employee> employees,
                                   List<Product> products, BenchmarkDataGenerator gen) {

        for (Order o : orders) {

            Customer c = customers.get(gen.nextInt(0, customers.size()));
            Employee e = employees.get(gen.nextInt(0, employees.size()));

            c.addOrder(o);
            e.addOrder(o);

            int productCount = gen.nextInt(0, 8);

            for (int i = 0; i < productCount; i++) {
                Product p = products.get(gen.nextInt(0, products.size()));
                o.addProduct(p);
            }
        }
    }
}
