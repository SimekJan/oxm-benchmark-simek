package cs.mff.uk.simek.document.queries.basic;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import cs.mff.uk.simek.document.northwind.Supplier;
import cs.mff.uk.simek.document.queries.Query;
import org.bson.Document;

import java.util.ArrayList;
import java.util.List;

/*
    Get all suppliers and their supplier count (even if 0)
 */
public class Query11_optional_traversal implements Query {
    @Override
    public void runQuery(MongoDatabase db) {
        MongoCollection<Supplier> suppliers = db.getCollection("Suppliers", Supplier.class);

        List<Document> result = suppliers.aggregate(List.of(
                new Document("$lookup",
                        new Document("from", "Suppliers")
                                .append("localField", "suppliedBy")
                                .append("foreignField", "_id")
                                .append("as", "suppliers")
                ),

                new Document("$addFields",
                        new Document("supplierCount",
                                new Document("$size", "$suppliers")
                        )
                ),

                new Document("$project",
                        new Document("companyName", 1)
                                .append("supplierCount", 1)
                )
        ), Document.class).into(new ArrayList<>());

        for (Document doc: result) {
            System.out.println(doc.toJson());
        }
    }
}
