package cs.mff.uk.simek.graph.northwind.helper;

import cs.mff.uk.simek.generator.DataProvider;
import cs.mff.uk.simek.graph.Neo4jSessionManager;
import lombok.extern.slf4j.Slf4j;
import org.neo4j.ogm.session.Session;
import cs.mff.uk.simek.graph.northwind.*;

import java.util.*;

@Slf4j
public class GenerateData {

    private static final int SUPPLIER_COUNT = 1_000;
    private static final int EMPLOYEE_COUNT = 2_000;
    private static final int PRODUCT_COUNT = 5_000;
    private static final int CUSTOMER_COUNT = 10_000;
    private static final int ORDER_COUNT = 20_000;

    public static void main(String[] args) {

        Session session = Neo4jSessionManager.getSession();
        DataProvider gen = new DataProvider();

        List<Supplier> suppliers = generateSuppliers(gen);
        List<Product> products = generateProducts(gen, suppliers);
        List<Employee> employees = generateEmployees(gen);
        List<Customer> customers = generateCustomers(gen);
        List<Order> orders = generateOrders(gen);

        for (int i = 0; i < suppliers.size(); i += 100) {
            session.save(suppliers.subList(i, Math.min(i + 100, suppliers.size())));
        }
        for (int i = 0; i < employees.size(); i += 100) {
            session.save(employees.subList(i, Math.min(i + 100, employees.size())));
        }
        for (int i = 0; i < products.size(); i += 100) {
            session.save(products.subList(i, Math.min(i + 100, products.size())));
        }
        for (int i = 0; i < customers.size(); i += 100) {
            session.save(customers.subList(i, Math.min(i + 100, customers.size())));
        }
        for (int i = 0; i < orders.size(); i += 100) {
            session.save(orders.subList(i, Math.min(i + 100, orders.size())));
        }

        log.info("All nodes saved");

        linkSuppliers(suppliers, gen);
        for (int i = 0; i < suppliers.size(); i += 100) {
            session.save(suppliers.subList(i, Math.min(i + 100, suppliers.size())), 1);
        }

        log.info("Suppliers linked");

        generateEmployeeHierarchy(employees, gen);
        for (int i = 0; i < employees.size(); i += 100) {
            session.save(employees.subList(i, Math.min(i + 100, employees.size())), 1);
        }

        log.info("Employees linked");

        linkOrders(orders, customers, employees, products, gen);
        for (int i = 0; i < orders.size(); i += 100) {
            session.save(orders.subList(i, Math.min(i + 100, orders.size())), 1);
        }

        log.info("Orders and products linked");
    }

    private static List<Supplier> generateSuppliers(DataProvider gen) {

        List<Supplier> list = new ArrayList<>();

        for (long i = 1; i <= SUPPLIER_COUNT; i++) {
            list.add(new Supplier(i, gen.nextCompanyName(), gen.nextCity()));
        }

        return list;
    }

    private static void linkSuppliers(List<Supplier> suppliers,
                                      DataProvider gen) {

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
            DataProvider gen,
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

    private static List<Employee> generateEmployees(DataProvider gen) {

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
                                                  DataProvider gen) {

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

    private static List<Customer> generateCustomers(DataProvider gen) {

        List<Customer> customers = new ArrayList<>();

        for (long i = 1; i <= CUSTOMER_COUNT; i++) {
            customers.add(new Customer(i, gen.nextCompanyName(), gen.nextCity()));
        }

        return customers;
    }

    private static List<Order> generateOrders(DataProvider gen) {

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
                                   List<Product> products, DataProvider gen) {

        for (Order o : orders) {

            Customer c = customers.get(gen.nextInt(0, customers.size()));
            Employee e = employees.get(gen.nextInt(0, employees.size()));

            c.addOrder(o);
            e.addOrder(o);

            int productCount = gen.nextInt(0, 4);

            for (int i = 0; i < productCount; i++) {
                Product p = products.get(gen.nextInt(0, products.size()));
                o.addProduct(p);
            }
        }
    }
}
