package cs.mff.uk.simek.relational.queries.basic;

import cs.mff.uk.simek.relational.queries.Query;
import cs.mff.uk.simek.relational.northwind.Employee;
import org.hibernate.Session;

import java.util.List;

/*
    Get employee by first_name. That is non-indexed column.
 */
public class Query2_select_non_indexed implements Query {
    @Override
    public void perform(Session session) {
        String hql =    "FROM Employees e " +
                        "WHERE e.firstName = :first_name";

        List<Employee> employees = session.createQuery(hql, Employee.class).setParameter("first_name", "Steven").getResultList();

        for (Employee e: employees) {
            System.out.println(e.getFirstName() + " - " + e.getLastName());
        }
    }
}
