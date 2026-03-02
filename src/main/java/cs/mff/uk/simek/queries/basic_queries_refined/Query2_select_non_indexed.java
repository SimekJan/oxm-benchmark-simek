package cs.mff.uk.simek.queries.basic_queries_refined;

import cs.mff.uk.simek.queries.Query;
import northwind.Employee;
import org.hibernate.Session;

/*
    Get employee by first_name. That is non-indexed column.
 */
public class Query2_select_non_indexed implements Query {
    @Override
    public void perform(Session session) {
        String hql =    "FROM Employees e " +
                        "WHERE e.firstName = :first_name";

        Employee e = session.createQuery(hql, Employee.class).setParameter("first_name", "Steven").uniqueResult();

        System.out.println(e.getFirstName() + " - " + e.getLastName());
    }
}
