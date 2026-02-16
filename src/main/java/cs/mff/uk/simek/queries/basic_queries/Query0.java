package cs.mff.uk.simek.queries.basic_queries;

import cs.mff.uk.simek.queries.Query;
import northwind.Employee;
import org.hibernate.Session;

public class Query0 implements Query {
    @Override
    public void perform(Session session) {
        String hql =    "FROM Employees e " +
                        "WHERE e.employeeId = :id";

        Employee e = session.createQuery(hql, Employee.class).setParameter("id", (short) 5).uniqueResult();

        System.out.println(e.getFirstName() + " - " + e.getLastName());
    }
}
