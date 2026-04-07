package cs.mff.uk.simek.document.queries.basic;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import cs.mff.uk.simek.document.northwind.Product;
import cs.mff.uk.simek.document.queries.Query;
import org.bson.Document;

import java.util.ArrayList;
import java.util.List;

import static com.mongodb.client.model.Accumulators.max;
import static com.mongodb.client.model.Aggregates.group;

/**
 * Find the most expensive product per supplier.
 */
public class Query06_max implements Query {
    @Override
    public void runQuery(MongoDatabase db) {

        MongoCollection<Product> products = db.getCollection("Products", Product.class);

        List<Document> result = products.aggregate(List.of(
                group("$supplier", max("maxPrice", "$unitPrice" ))
        ), Document.class).into(new ArrayList<>());

        for (Document doc : result) {
            System.out.println(doc.get("_id") + ": " + doc.getInteger("maxPrice"));
        }
    }
}
