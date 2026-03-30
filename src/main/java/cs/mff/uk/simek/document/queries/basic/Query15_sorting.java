package cs.mff.uk.simek.document.queries.basic;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Sorts;
import cs.mff.uk.simek.document.northwind.Product;
import cs.mff.uk.simek.document.queries.Query;

import java.util.ArrayList;
import java.util.List;

import static com.mongodb.client.model.Aggregates.sort;

/*
    Products based on unit price
    (not indexed column)
 */
public class Query15_sorting implements Query {
    @Override
    public void runQuery(MongoDatabase db) {
        MongoCollection<Product> products = db.getCollection("Products", Product.class);

        List<Product> results = products.aggregate(List.of(
                sort(Sorts.ascending("unitPrice"))
        ), Product.class).into(new ArrayList<>());

        for (Product p: results) {
            System.out.println(p.getProductName() + " " + p.getUnitPrice());
        }
    }
}
