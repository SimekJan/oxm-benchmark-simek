package cs.mff.uk.simek.document.queries.complex;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import cs.mff.uk.simek.document.queries.Query;
import org.bson.Document;

import java.util.ArrayList;
import java.util.List;

/**
 * Join employees with orders and order by employee name.
 */
public class C_Query2_join_sort implements Query {

    @Override
    public void runQuery(MongoDatabase db) {

        MongoCollection<Document> orders = db.getCollection("Orders");

        List<Document> results = orders.aggregate(List.of(

                new Document("$lookup",
                        new Document("from", "Employees")
                                .append("localField", "employee")
                                .append("foreignField", "_id")
                                .append("as", "employee")
                ),

                new Document("$unwind", "$employee"),

                new Document("$project",
                        new Document("order", "$orderId")
                                .append("firstName", "$employee.firstName")
                                .append("lastName", "$employee.lastName")
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
