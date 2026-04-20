package cs.mff.uk.simek.relational.queries.basic;

import cs.mff.uk.simek.relational.queries.Query;
import cs.mff.uk.simek.relational.northwind.Employee;
import org.hibernate.Session;

/*
    Get employee with specific id. That is filter by indexed column.
 */
public class Query1_select_indexed implements Query {
    @Override
    public void perform(Session session) {
        String hql =    "FROM Employees e " +
                        "WHERE e.employeeId = :id";

        Employee e = session.createQuery(hql, Employee.class).setParameter("id", 5L).uniqueResult();

        System.out.println(e.getFirstName() + " - " + e.getLastName());
    }
}
