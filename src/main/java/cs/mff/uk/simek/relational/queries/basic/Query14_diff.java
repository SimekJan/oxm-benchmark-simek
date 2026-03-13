package cs.mff.uk.simek.relational.queries.basic;

import cs.mff.uk.simek.relational.queries.Query;
import org.hibernate.Session;

import java.util.List;

/*
    find customers without orders =
        all customer_ids - customers_ids in orders

    this is not a best example for set operation, there are better alternatives for this task
    like NOT EXIST / LEFT JOIN
 */
public class Query14_diff implements Query {
    @Override
    public void perform(Session session) {
        String sql =    "SELECT customer_id " +
                        "FROM Customers " +
                        "EXCEPT " +
                        "SELECT customer_id " +
                        "FROM Orders";

        List<?> results = session.createNativeQuery(sql).getResultList();

        for (Object line: results) {
            System.out.println(line);
        }
    }
}

/*
    TODO: lze udělat přímo v HQL pomocí NOT IN / NOT EXISTS
    přidat další
 */
