package cs.mff.uk.simek.document.queries.complex;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import cs.mff.uk.simek.document.queries.Query;
import org.bson.Document;

import java.util.ArrayList;
import java.util.List;

/**
 * Join suppliers which are from cities starting wit 'P' with their "cat2" products.
 */
public class C_Query5_filter_join implements Query {

    @Override
    public void runQuery(MongoDatabase db) {

        MongoCollection<Document> products = db.getCollection("Products");

        List<Document> results = products.aggregate(List.of(

                new Document("$match",
                        new Document("category", "cat2")
                ),

                new Document("$lookup",
                        new Document("from", "Suppliers")
                                .append("localField", "supplier")
                                .append("foreignField", "_id")
                                .append("as", "supplier")
                ),

                new Document("$unwind", "$supplier"),

                new Document("$match",
                        new Document("supplier.city",
                                new Document("$regex", "^P")
                        )
                ),

                new Document("$project",
                        new Document("_id", 0)
                                .append("product", "$productName")
                                .append("company", "$supplier.companyName")
                )

        ), Document.class).into(new ArrayList<>());

        for (Document row : results) {
            String product = row.getString("product");
            String company = row.getString("company");

            System.out.println(company + " - " + product);
        }
    }
}
