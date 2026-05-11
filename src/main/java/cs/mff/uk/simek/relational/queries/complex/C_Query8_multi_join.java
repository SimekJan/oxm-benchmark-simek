package cs.mff.uk.simek.relational.queries.complex;

import cs.mff.uk.simek.relational.queries.Query;
import org.hibernate.Session;

import java.util.List;

/*
 * Join Customers, Employees, Orders, Products and Suppliers. (Indexed)
 */
public class C_Query8_multi_join implements Query {
    @Override
    public void perform(Session session) {
        String hql =    "SELECT c.companyName, e.firstName, o.orderId, p.productName, s.companyName " +
                        "FROM Orders o " +
                        "JOIN o.products p " +
                        "JOIN p.supplier s " +
                        "JOIN o.customer c " +
                        "JOIN o.employee e ";

        List<Object[]> result = session.createQuery(hql, Object[].class).getResultList();

        for (Object[] row: result) {
            String customerCompanyName = (String) row[0];
            String employeeName = (String) row[1];
            Long orderId = (Long) row[2];
            String productName = (String) row[3];
            String supplierCompanyName = (String) row[4];

            System.out.println(customerCompanyName + " " + employeeName + " " + orderId + " " + productName + " " + supplierCompanyName);
        }

    }
}
