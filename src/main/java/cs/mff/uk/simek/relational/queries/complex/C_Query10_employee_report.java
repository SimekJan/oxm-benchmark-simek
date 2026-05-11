package cs.mff.uk.simek.relational.queries.complex;

import cs.mff.uk.simek.relational.queries.Query;
import org.hibernate.Session;

import java.time.LocalDate;
import java.util.List;

/**
 *  Create a complex real-world-like report including employee info
 */
public class C_Query10_employee_report implements Query {
    @Override
    public void perform(Session session) {
        String hql = """
                        SELECT
                            e.employeeId,
                            e.firstName,
                            e.lastName,
                    
                            (SELECT COUNT(o)
                             FROM Orders o
                             WHERE o.employee = e),
                    
                            (SELECT SUM(p.unitPrice)
                             FROM Orders o
                             JOIN o.products p
                             WHERE o.employee = e),
                    
                            (SELECT COUNT(s)
                             FROM Employees s
                             WHERE s.reportsTo = e),
                    
                            EXTRACT(YEAR FROM AGE(:current_date, e.hireDate)),
                    
                            EXTRACT(YEAR FROM AGE(:current_date, e.birthDate))
                    
                        FROM Employees e
                    """;

        List<Object[]> result = session.createQuery(hql, Object[].class)
                                    .setParameter("current_date", LocalDate.now())
                                    .getResultList();

        for (Object[] row: result) {
            Long employeeId = (Long) row[0];
            String firstName = (String) row[1];
            String lastName = (String) row[2];
            Long numberOfOrders = (Long) row[3];
            Double totalPrice = (Double) row[4];
            Long numberOfSubordinates = (Long) row[5];
            Integer yearsSinceHire = (Integer) row[6];
            Integer age = (Integer) row[7];

            System.out.println("Employee: " + employeeId + " " + firstName + " " + lastName);
            System.out.println("Manages " + numberOfOrders + " orders, with total price of " + totalPrice);
            System.out.println("Supervises " + numberOfSubordinates + " employee(s)");
            System.out.println("Works for " + yearsSinceHire + " years. Aged: " + age);
            System.out.println("-----------------------------------------------------------------------------------");
        }
    }
}
