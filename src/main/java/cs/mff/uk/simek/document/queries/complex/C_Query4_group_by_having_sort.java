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

        MongoCollection<Document> orders = db.getCollection("Orders");

        List<Document> results = orders.aggregate(List.of(

                new Document("$group",
                        new Document("_id", "$employee")
                                .append("orderCount", new Document("$sum", 1))
                ),

                new Document("$lookup",
                        new Document("from", "Employees")
                                .append("localField", "_id")
                                .append("foreignField", "_id")
                                .append("as", "employee")
                ),

                new Document("$unwind", "$employee"),

                new Document("$match",
                        new Document("orderCount",
                                new Document("$gt", 1)
                        )
                ),

                new Document("$project",
                        new Document("_id", 0)
                                .append("firstName", "$employee.firstName")
                                .append("lastName", "$employee.lastName")
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
