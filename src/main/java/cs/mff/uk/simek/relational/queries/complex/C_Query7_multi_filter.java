package cs.mff.uk.simek.relational.queries.complex;

import cs.mff.uk.simek.relational.northwind.Employee;
import cs.mff.uk.simek.relational.queries.Query;
import org.hibernate.Session;

import java.time.LocalDate;
import java.util.List;

/*
 * Select only employees with firstname starting with B, lastname starting with M,
 * city 'Prague' birthdate before 1991 and date of hire after 2018.
 */
public class C_Query7_multi_filter implements Query {
    @Override
    public void perform(Session session) {
        String hql =    "FROM Employees e " +
                        "WHERE e.firstName LIKE 'R%' " +
                            "AND e.city = 'Reykjavik' " +
                            "AND e.birthDate <= :birth_date " +
                            "AND e.hireDate >= :start_date ";

        List<Employee> result = session.createQuery(hql, Employee.class)
                .setParameter("birth_date", LocalDate.of(2000,1,1))
                .setParameter("start_date", LocalDate.of(2014,1,1))
                .getResultList();

        for (Employee e: result) {
            System.out.println(e.getFirstName() + " " + e.getLastName());
        }
    }
}
