package cs.mff.uk.simek.relational.queries.basic;

import cs.mff.uk.simek.relational.northwind.Product;
import cs.mff.uk.simek.relational.queries.Query;
import cs.mff.uk.simek.relational.northwind.Employee;
import org.hibernate.Session;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

/*
    Get all products in price range 9.5 - 12.5 (Range query).
 */
public class Query4_range_query_non_indexed implements Query {
    @Override
    public void perform(Session session) {
        String hql =    "FROM Products p " +
                        "WHERE p.unitPrice BETWEEN :price_from AND :price_to";

        List<Product> products = session.createQuery(hql, Product.class)
                                    .setParameter("price_from", 9.5D)
                                    .setParameter("price_to", 12.5D)
                                    .getResultList();

        System.out.println("Found: " + products.size() + " products.");
        for (Product p : products) {
            System.out.println(p.getProductName() + ": " + p.getUnitPrice());
        }
    }
}
