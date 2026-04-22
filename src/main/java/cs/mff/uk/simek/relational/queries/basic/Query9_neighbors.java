package cs.mff.uk.simek.relational.queries.basic;

import cs.mff.uk.simek.relational.northwind.Supplier;
import cs.mff.uk.simek.relational.queries.Query;
import org.hibernate.Session;

import java.util.List;

/*
    Find all direct and indirect connections between suppliers (max depth = 2).
 */
public class Query9_neighbors implements Query {
    @Override
    public void perform(Session session) {

        String hql1 = """
                    SELECT DISTINCT s, o1
                    FROM Suppliers s
                    JOIN s.suppliesTo o1
                    WHERE s.id <> o1.id
                    """;

        List<Object[]> results1 = session.createQuery(hql1).getResultList();

        for (Object[] row : results1) {
            Supplier from = (Supplier) row[0];
            Supplier to = (Supplier) row[1];

            System.out.println(from.getCompanyName() + " -> " + to.getCompanyName() + " with depth 1");
        }

        String hql2 = """
                    SELECT DISTINCT s, o2
                    FROM Suppliers s
                    JOIN s.suppliesTo o1
                    JOIN o1.suppliesTo o2
                    WHERE s.id <> o2.id
                    """;

        List<Object[]> results2 = session.createQuery(hql2).getResultList();

        for (Object[] row : results2) {
            Supplier from = (Supplier) row[0];
            Supplier to = (Supplier) row[1];

            System.out.println(from.getCompanyName() + " -> " + to.getCompanyName() + " with depth 2");
        }
    }
}

/*
    TODO: Takhle nebo native SQL s WITH RECOURSIVE, oboje nic moc
 */
