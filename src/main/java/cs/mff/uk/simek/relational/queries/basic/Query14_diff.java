package cs.mff.uk.simek.relational.queries.basic;

import cs.mff.uk.simek.relational.queries.Query;
import org.hibernate.Session;

import java.util.List;

/*
    Find all Customers without an order (all customers - (diff) customer_ids in orders)

    this is not a best example for set operation, there are better alternatives for this task
    like NOT EXIST / LEFT JOIN
 */
public class Query14_diff implements Query {
    @Override
    public void perform(Session session) {
        String hql = """
                        SELECT c.companyName
                        FROM Customers c
                        WHERE NOT EXISTS (
                            SELECT 1
                            FROM Orders o
                            WHERE o.customer.id = c.id
                        )
                    """;

        List<String> results = session.createQuery(hql, String.class).getResultList();

        for (String companyName : results) {
            System.out.println(companyName);
        }
    }
}

/*
    TODO: lze udělat přímo v HQL pomocí NOT IN / NOT EXISTS
    přidat další
 */
