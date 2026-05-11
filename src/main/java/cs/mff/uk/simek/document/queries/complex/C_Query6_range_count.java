package cs.mff.uk.simek.document.queries.complex;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import cs.mff.uk.simek.document.queries.Query;
import org.bson.Document;

import java.util.ArrayList;
import java.util.List;

/**
 * Count products with unitPrice between 10 and 15 per category
 */
public class C_Query6_range_count implements Query {

    @Override
    public void runQuery(MongoDatabase db) {

        MongoCollection<Document> products = db.getCollection("Products");

        List<Document> results = products.aggregate(List.of(

                new Document("$match",
                        new Document("unitPrice",
                                new Document("$gte", 10)
                                        .append("$lte", 15)
                        )
                ),

                new Document("$group",
                        new Document("_id", "$category")
                                .append("productCount",
                                        new Document("$sum", 1)
                                )
                ),

                new Document("$project",
                        new Document("_id", 0)
                                .append("category", "$_id")
                                .append("productCount", 1)
                )

        ), Document.class).into(new ArrayList<>());

        for (Document row : results) {
            String category = row.getString("category");
            Integer count = row.getInteger("productCount");

            System.out.println(category + ": " + count);
        }
    }
}
