package cs.mff.uk.simek.document.queries.complex;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import cs.mff.uk.simek.document.queries.Query;
import org.bson.Document;

import java.util.ArrayList;
import java.util.List;

/**
 * Select products with bellow average price.
 */
public class C_Query9_subquery implements Query {
    @Override
    public void runQuery(MongoDatabase db) {

        MongoCollection<Document> products = db.getCollection("Products");

        List<Document> results = products.aggregate(List.of(

                new Document("$group",
                        new Document("_id", null)
                                .append("avgPrice", new Document("$avg", "$unitPrice"))
                ),

                new Document("$lookup",
                        new Document("from", "Products")
                                .append("pipeline", List.of(
                                        new Document("$match", new Document())
                                ))
                                .append("as", "products")
                ),

                new Document("$unwind", "$products"),

                new Document("$match",
                        new Document("$expr",
                                new Document("$lte", List.of("$products.unitPrice", "$avgPrice"))
                        )
                ),

                new Document("$project",
                        new Document("_id", 0)
                                .append("productName", "$products.productName")
                )

        ), Document.class).into(new ArrayList<>());

        for (Document row : results) {
            System.out.println(row.getString("productName"));
        }
    }
}
