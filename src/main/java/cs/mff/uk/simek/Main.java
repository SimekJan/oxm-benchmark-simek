package cs.mff.uk.simek;

import northwind.*;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        SessionFactory sessionFactory = new Configuration()
                .configure("hibernate.cfg.xml")
                .addAnnotatedClass(Category.class)
                .addAnnotatedClass(Product.class)
                .addAnnotatedClass(OrderDetail.class)
                .addAnnotatedClass(Employee.class)
                .buildSessionFactory();

        Session session = sessionFactory.openSession();

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
            System.out.println("-------------------------------------------------------------------------");
            for (Product p : products) {
                System.out.println(p.getProductName() + " - " + p.getUnitPrice() + " - " + p.getUnitsInStock());
            }
            System.out.println("-------------------------------------------------------------------------");
            for (Order o : orders) {
                System.out.println(o.getShipName() + " - " + o.getShipRegion());
            }

            session.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            session.close();
            sessionFactory.close();
        }
    }
}