package cs.mff.uk.simek.document.queries.complex;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import cs.mff.uk.simek.document.queries.Query;
import org.bson.Document;

import java.util.ArrayList;
import java.util.List;

/**
 * Join suppliers which are from cities starting wit 'P' with their "Packaging Materials" products.
 */
public class C_Query5_filter_join implements Query {

    @Override
    public void runQuery(MongoDatabase db) {

        MongoCollection<Document> suppliers = db.getCollection("Suppliers");

        List<Document> results = suppliers.aggregate(List.of(

                new Document("$match",
                        new Document("city",
                                new Document("$regex", "^P")
                        )
                ),

                new Document("$lookup",
                        new Document("from", "Products")
                                .append("localField", "_id")
                                .append("foreignField", "supplier")
                                .append("as", "products")
                ),

                new Document("$unwind", "$products"),

                new Document("$match",
                        new Document("products.category", "Packaging Materials")
                ),

                new Document("$project",
                        new Document("_id", 0)
                                .append("product", "$products.productName")
                                .append("company", "$companyName")
                )
        ), Document.class).into(new ArrayList<>());

        for (Document row : results) {
            String product = row.getString("product");
            String company = row.getString("company");

            System.out.println(company + " - " + product);
        }
    }
}
