package cs.mff.uk.simek.graph.queries.basic;

import cs.mff.uk.simek.graph.northwind.Product;
import cs.mff.uk.simek.graph.queries.Query;
import org.neo4j.ogm.cypher.BooleanOperator;
import org.neo4j.ogm.cypher.ComparisonOperator;
import org.neo4j.ogm.cypher.Filter;
import org.neo4j.ogm.cypher.Filters;
import org.neo4j.ogm.session.Session;

import java.util.Collection;

public class Query4_range_query_non_indexed implements Query {
    @Override
    public void perform(Session session) {

        Filter maxUnitPrice = new Filter("unitPrice", ComparisonOperator.LESS_THAN, 13);
        Filter minUnitPrice = new Filter("unitPrice", ComparisonOperator.GREATER_THAN, 9);
        minUnitPrice.setBooleanOperator(BooleanOperator.AND);

        Filters range = new Filters();
        range.add(maxUnitPrice);
        range.add(minUnitPrice);

        Collection<Product> result = session.loadAll(Product.class, range);

        System.out.println("Found " + result.size() + " products in price range.");
    }
}
