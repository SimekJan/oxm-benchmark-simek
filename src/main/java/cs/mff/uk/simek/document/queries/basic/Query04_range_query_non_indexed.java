package cs.mff.uk.simek.document.queries.basic;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import cs.mff.uk.simek.document.northwind.Product;
import cs.mff.uk.simek.document.queries.Query;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static com.mongodb.client.model.Filters.*;

/*
    Get all products in price range 9.5 - 12.5 (Range query).
 */
public class Query04_range_query_non_indexed implements Query {
    @Override
    public void runQuery(MongoDatabase db) {
        MongoCollection<Product> products = db.getCollection("Products", Product.class);

        List<Product> res = products.find(and(
                gte("unitPrice", new BigDecimal("9.5")),
                lte("unitPrice", new BigDecimal("12.5"))
        )).into(new ArrayList<>());

        System.out.println("Number of products found: " + res.size());
        for (Product p: res) {
            System.out.println(p.getProductName() + ": " + p.getUnitPrice());
        }
    }
}
