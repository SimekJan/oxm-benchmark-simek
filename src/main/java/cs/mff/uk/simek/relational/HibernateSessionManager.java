package cs.mff.uk.simek.relational;

import cs.mff.uk.simek.relational.northwind.*;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import java.io.File;
import java.util.List;

public class HibernateSessionManager {

    public static Session getSession() {
        SessionFactory sessionFactory = new Configuration()
                // Workaround needed, IntelliJ did not copy the config file to target/ or out/
                // .configure("hibernate.cfg.xml.xml")
                .configure(
                        new File("src/main/resources/hibernate.cfg.xml")
                )
                .addAnnotatedClass(Customer.class)
                .addAnnotatedClass(Employee.class)
                .addAnnotatedClass(Order.class)
                .addAnnotatedClass(Product.class)
                .addAnnotatedClass(Supplier.class)
                .buildSessionFactory();

        return sessionFactory.openSession();
    }

    public static void main(String[] args) {

        Session session = getSession();

        try {
            session.beginTransaction();

            List<Customer> customers = session.createQuery("from Customers", Customer.class).getResultList();
            List<Employee> employees = session.createQuery("from Employees", Employee.class).getResultList();
            List<Product> products = session.createQuery("from Products", Product.class).getResultList();
            List<Order> orders = session.createQuery("from Orders", Order.class).getResultList();

            System.out.println("-------------------------------------------------------------------------");
            for (Customer c : customers) {
                System.out.println(c.getCustomerId() + " - " + c.getCompanyName());
            }
            System.out.println("-------------------------------------------------------------------------");
            System.out.println("-------------------------------------------------------------------------");
            for (Employee e : employees) {
                String managerName = (e.getReportsTo() != null) ? e.getReportsTo().getLastName() : "-";
                System.out.println(e.getLastName() + " - " + e.getFirstName() + " - " + e.getCity()
                        + " - " + e.getBirthDate() + " - " + managerName + " - " + e.getEmployeeId());
            }
       /*     System.out.println("-------------------------------------------------------------------------");
            for (Product p : products) {
                System.out.println(p.getProductName() + " - " + p.getUnitPrice() + " - " + p.getUnitsInStock());
            }
            System.out.println("-------------------------------------------------------------------------");
            for (Order o : orders) {
                System.out.println(o.getShipName() + " - " + o.getShipRegion());
            }*/

            session.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}