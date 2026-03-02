package cs.mff.uk.simek.queries.basic_queries_refined;

import cs.mff.uk.simek.queries.Query;
import northwind.Employee;
import org.hibernate.Session;

/*
    Get employee with specific id. That is filter by indexed column.
 */
public class Query1_select_indexed implements Query {
    @Override
    public void perform(Session session) {
        String hql =    "FROM Employees e " +
                        "WHERE e.employeeId = :id";

        Employee e = session.createQuery(hql, Employee.class).setParameter("id", (short) 5).uniqueResult();

        System.out.println(e.getFirstName() + " - " + e.getLastName());
    }
}

/*
    TODO: co víc komplexní filtrování přes více sloupců ?
 */
