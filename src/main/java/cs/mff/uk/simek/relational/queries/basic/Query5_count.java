package cs.mff.uk.simek.relational.queries.basic;

import cs.mff.uk.simek.relational.queries.Query;
import org.hibernate.Session;

import java.util.List;

/*
    Count the number of employees per city.
 */
public class Query5_count implements Query {
    @Override
    public void perform(Session session) {
        String hql =    "SELECT e.city, COUNT(e) " +
                        "FROM Employees e " +
                        "GROUP BY e.city";

        List<Object[]> results = session.createQuery(hql, Object[].class).getResultList();

        for (Object[] line: results) {
            System.out.println(line[0] + ": " + line[1]);
        }
    }
}

/*
*       TODO: Co čistě COUNT (a MAX) bez GROUP BY ?
*
*       GROUPing bude potenciálně stát další operace.
*       COUNT samotný může mít taky rozdíly v implementaci: projít prvky / číst z metadat.
*
*       Ostatní články ale mají pouze verze s GROUP BY
* */
