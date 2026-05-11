package cs.mff.uk.simek.document_embedded.queries.complex;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import cs.mff.uk.simek.document_embedded.queries.Query;
import org.bson.Document;

import java.util.ArrayList;
import java.util.List;

/**
 * Join employees with orders and order by employee name.
 */
public class C_Query2_join_sort implements Query {

    @Override
    public void runQuery(MongoDatabase db) {

        MongoCollection<Document> employees = db.getCollection("Employees");

        List<Document> results = employees.aggregate(List.of(

                new Document("$unwind", "$orders"),

                new Document("$project",
                        new Document("_id", 0)
                                .append("order", "$orders.orderId")
                                .append("firstName", "$firstName")
                                .append("lastName", "$lastName")
                ),

                new Document("$sort",
                        new Document("lastName", 1)
                                .append("firstName", 1)
                )

        ), Document.class).into(new ArrayList<>());

        for (Document row : results) {
            Long orderId = row.getLong("order");
            String firstName = row.getString("firstName");
            String lastName = row.getString("lastName");

            System.out.println("Order " + orderId + " -> " + firstName + " " + lastName);
        }
    }
}
