package cs.mff.uk.simek.document.queries_v2.basic;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import cs.mff.uk.simek.document.northwind.Product;
import cs.mff.uk.simek.document.queries_v2.DocumentQuery;
import cs.mff.uk.simek.query_params.params.Q4_Params;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static com.mongodb.client.model.Filters.*;

/**
 * Get all products in price range 9.5 - 12.5 (Range query).
 */
public class Query04_range_query_non_indexed implements DocumentQuery<Q4_Params> {

    @Override
    public void run(Q4_Params params, MongoDatabase db) {
        MongoCollection<Product> products = db.getCollection("Products", Product.class);

        List<Product> res = products.find(and(
                gte("unitPrice", BigDecimal.valueOf(params.unitPriceFrom())),
                lte("unitPrice", BigDecimal.valueOf(params.unitPriceTo()))
        )).into(new ArrayList<>());

        System.out.println("Number of products found: " + res.size());
        for (Product p: res) {
            System.out.println(p.getProductName() + ": " + p.getUnitPrice());
        }
    }
}
