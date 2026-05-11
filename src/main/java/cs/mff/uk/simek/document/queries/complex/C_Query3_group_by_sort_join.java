package cs.mff.uk.simek.document.queries.complex;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import cs.mff.uk.simek.document.queries.Query;
import org.bson.Document;

import java.util.ArrayList;
import java.util.List;

/**
 * Order customers from most orders to least (include order count).
 */
public class C_Query3_group_by_sort_join implements Query {

    @Override
    public void runQuery(MongoDatabase db) {

        MongoCollection<Document> customers = db.getCollection("Customers");

        List<Document> results = customers.aggregate(List.of(

                new Document("$lookup",
                        new Document("from", "Orders")
                                .append("localField", "_id")
                                .append("foreignField", "customer")
                                .append("as", "orders")
                ),

                new Document("$addFields",
                        new Document("orderCount",
                                new Document("$size", "$orders")
                        )
                ),

                new Document("$project",
                        new Document("orders", 0)
                ),

                new Document("$sort",
                        new Document("orderCount", -1)
                )

        ), Document.class).into(new ArrayList<>());

        for (Document row : results) {

            String customer = row.getString("companyName");
            Long customerId = row.getLong("customerId");
            Integer count = row.getInteger("orderCount");

            System.out.println(customerId + " - " + customer + " - " + count);
        }
    }
}
