package cs.mff.uk.simek.relational.queries.basic;

import cs.mff.uk.simek.query_params.params.Q4_Params;
import cs.mff.uk.simek.relational.northwind.Product;
import cs.mff.uk.simek.relational.queries.RelationalQuery;

import java.util.List;

/**
 * Get all products in price range.
 */
public class Query04_range_query_non_indexed implements RelationalQuery<Q4_Params> {

    @Override
    public void run(Q4_Params params) {
        System.out.println("----------PostgreSQL-Q4----------");

        String hql = "FROM Products p " +
            "WHERE p.unitPrice BETWEEN :price_from AND :price_to";

        List<Product> products = session.createQuery(hql, Product.class)
            .setParameter("price_from", params.unitPriceFrom().doubleValue())
            .setParameter("price_to", params.unitPriceTo().doubleValue())
            .getResultList();

        System.out.println("Found: " + products.size() + " products.");
        // for (Product p : products) {
        //    System.out.println(p.getProductName() + ": " + p.getUnitPrice());
        // }
    }
}
