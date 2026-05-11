package cs.mff.uk.simek.document.queries.complex;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import cs.mff.uk.simek.document.queries.Query;
import org.bson.Document;

import java.util.ArrayList;
import java.util.List;

/**
 * Max price of products with name starting with "S" per category.
 */
public class C_Query1_filter_max implements Query {

    @Override
    public void runQuery(MongoDatabase db) {

        MongoCollection<Document> products = db.getCollection("Products");

        List<Document> results = products.aggregate(List.of(
                new Document("$match",
                        new Document("productName",
                                new Document("$regex", "^S")
                        )
                ),

                new Document("$group",
                        new Document("_id", "$category")
                                .append("maxUnitPrice",
                                        new Document("$max", "$unitPrice")
                                )
                )
        ), Document.class).into(new ArrayList<>());

        for (Document row : results) {
            String category = row.getString("_id");
            Double maxUnitPrice = row.getDouble("maxUnitPrice");

            System.out.println(category + ": " + maxUnitPrice);
        }
    }
}
