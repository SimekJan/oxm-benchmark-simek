package cs.mff.uk.simek.relational.queries.basic;

import cs.mff.uk.simek.relational.queries.Query;
import cs.mff.uk.simek.relational.northwind.Employee;
import org.hibernate.Session;

import java.time.LocalDate;
import java.util.List;

/*
    Get all users born between 1.1.1991 and 1.1.1996 (Range query).
 */
public class Query4_range_query_non_indexed implements Query {
    @Override
    public void perform(Session session) {
        String hql =    "FROM Employees e " +
                        "WHERE e.birthDate BETWEEN :birth_from AND :birth_to";

        List<Employee> empls = session.createQuery(hql, Employee.class)
                                    .setParameter("birth_from", LocalDate.of(1991, 1, 1))
                                    .setParameter("birth_to", LocalDate.of(1996,1,1))
                                    .getResultList();

        for (Employee e : empls) {
            System.out.println(e.getFirstName() + " " + e.getLastName() + ": " + e.getBirthDate());
        }
    }
}
