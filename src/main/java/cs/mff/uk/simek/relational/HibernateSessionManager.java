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
                .addAnnotatedClass(Category.class)
                .addAnnotatedClass(Customer.class)
                .addAnnotatedClass(CustomerDemographic.class)
                .addAnnotatedClass(Employee.class)
                .addAnnotatedClass(Order.class)
                .addAnnotatedClass(OrderDetail.class)
                .addAnnotatedClass(Product.class)
                .addAnnotatedClass(Region.class)
                .addAnnotatedClass(Shipper.class)
                .addAnnotatedClass(Supplier.class)
                .addAnnotatedClass(Territory.class)
                .addAnnotatedClass(UsState.class)
                .buildSessionFactory();

        return sessionFactory.openSession();
    }

    public static void main(String[] args) {

        Session session = getSession();

        try {
            session.beginTransaction();

            List<Category> categories = session.createQuery("from Categories", Category.class).getResultList();
            List<Customer> customers = session.createQuery("from Customers", Customer.class).getResultList();
            List<OrderDetail> orderDetails = session.createQuery("from OrderDetails", OrderDetail.class).getResultList();
            List<Employee> employees = session.createQuery("from Employees", Employee.class).getResultList();
            List<Product> products = session.createQuery("from Products", Product.class).getResultList();
            List<Order> orders = session.createQuery("from Orders", Order.class).getResultList();

            for (Category c : categories) {
                System.out.println(c.getCategoryId() + " - " + c.getCategoryName());
            }
            System.out.println("-------------------------------------------------------------------------");
            for (Customer c : customers) {
                System.out.println(c.getCustomerId() + " - " + c.getCompanyName());
            }
            System.out.println("-------------------------------------------------------------------------");
            for (OrderDetail o : orderDetails) {
                System.out.println(o.getId() + " - " + o.getQuantity() + " - " + o.getDiscount());
            }
            System.out.println("-------------------------------------------------------------------------");
            for (Employee e : employees) {
                String managerName = (e.getManager() != null) ? e.getManager().getLastName() : "-";
                System.out.println(e.getLastName() + " - " + e.getFirstName() + " - " + e.getCountry()
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