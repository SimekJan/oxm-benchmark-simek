package cs.mff.uk.simek.document.queries.complex;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import cs.mff.uk.simek.document.queries.Query;
import org.bson.Document;

import java.util.ArrayList;
import java.util.List;


/**
 * Order employees from most orders to least (include order count), show only employees having more than one order.
 */
public class C_Query4_group_by_having_sort implements Query {

    @Override
    public void runQuery(MongoDatabase db) {

        MongoCollection<Document> employees = db.getCollection("Employees");

        List<Document> results = employees.aggregate(List.of(

                new Document("$lookup",
                        new Document("from", "Orders")
                                .append("localField", "_id")
                                .append("foreignField", "employee")
                                .append("as", "orders")
                ),

                new Document("$addFields",
                        new Document("orderCount",
                                new Document("$size", "$orders")
                        )
                ),

                new Document("$match",
                        new Document("orderCount",
                                new Document("$gt", 1)
                        )
                ),

                new Document("$project",
                        new Document("_id", 0)
                                .append("firstName", 1)
                                .append("lastName", 1)
                                .append("orderCount", 1)
                ),

                new Document("$sort",
                        new Document("orderCount", -1)
                )

        ), Document.class).into(new ArrayList<>());

        for (Document row : results) {
            String firstName = row.getString("firstName");
            String lastName = row.getString("lastName");
            Integer count = row.getInteger("orderCount");

            System.out.println(firstName + " " + lastName + " - " + count);
        }
    }
}
