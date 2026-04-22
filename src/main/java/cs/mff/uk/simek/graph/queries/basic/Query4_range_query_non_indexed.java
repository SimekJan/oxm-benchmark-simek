package cs.mff.uk.simek.graph.queries.basic;

import cs.mff.uk.simek.graph.northwind.Product;
import cs.mff.uk.simek.graph.queries.Query;
import org.neo4j.ogm.cypher.BooleanOperator;
import org.neo4j.ogm.cypher.ComparisonOperator;
import org.neo4j.ogm.cypher.Filter;
import org.neo4j.ogm.cypher.Filters;
import org.neo4j.ogm.session.Session;

import java.util.Collection;

/*
    Get all products in price range 9.5 - 12.5 (Range query).
 */
public class Query4_range_query_non_indexed implements Query {
    @Override
    public void perform(Session session) {

        Filter minLastName = new Filter("unitPrice", ComparisonOperator.GREATER_THAN_EQUAL, 9.5D);
        Filter maxLastName = new Filter("unitPrice", ComparisonOperator.LESS_THAN, 12.5D);
        minLastName.setBooleanOperator(BooleanOperator.AND);

        Filters range = maxLastName.and(minLastName);

        Collection<Product> result = session.loadAll(Product.class, range);

        System.out.println("Found " + result.size() + " products in range.");
        for (Product p : result) {
            System.out.println(p.getProductName() + ": " + p.getUnitPrice());
        }
    }
}
