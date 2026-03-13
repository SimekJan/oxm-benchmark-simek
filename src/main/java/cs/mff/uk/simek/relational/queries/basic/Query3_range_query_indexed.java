package cs.mff.uk.simek.relational.queries.basic;

import cs.mff.uk.simek.relational.queries.Query;
import cs.mff.uk.simek.relational.northwind.Employee;
import org.hibernate.Session;

import java.util.List;

/*
    Get all users with employee_id between 5 and 10 (Range query).
 */
public class Query3_range_query_indexed implements Query {
    @Override
    public void perform(Session session) {
        String hql =    "FROM Employees e " +
                        "WHERE e.employeeId BETWEEN :id_min AND :id_max";

        List<Employee> empls = session.createQuery(hql, Employee.class)
                        .setParameter("id_min", (short) 5)
                        .setParameter("id_max", (short) 10)
                        .getResultList();

        for (Employee e : empls) {
            System.out.println(e.getEmployeeId() + ": " + e.getFirstName() + " " + e.getLastName());
        }
    }
}
