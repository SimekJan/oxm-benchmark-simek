package cs.mff.uk.simek.relational.queries.basic;

import cs.mff.uk.simek.relational.queries.Query;
import org.hibernate.Session;

import java.util.List;

/*
    Show list of all employees and count of others that reports to them (0 if none)
 */
public class Query11_optional_traversal implements Query {
    @Override
    public void perform(Session session) {
        String hql =    "SELECT e.employeeId, e.firstName, e.lastName, COUNT(o) " +
                        "FROM Employees e " +
                        "LEFT JOIN e.subordinates o " +
                        "GROUP BY e.employeeId, e.firstName, e.lastName ";

        List<Object[]> results = session.createQuery(hql, Object[].class).getResultList();

        for (Object[] line: results) {
            System.out.println(line[3] + " people reports to: " + line[0] + ", " + line[1] + " " + line[2]);
        }
    }
}
