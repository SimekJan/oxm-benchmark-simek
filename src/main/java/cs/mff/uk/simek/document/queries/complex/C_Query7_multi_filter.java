package cs.mff.uk.simek.document.queries.complex;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import cs.mff.uk.simek.document.queries.Query;
import org.bson.Document;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/*
 * Select only employees with firstname starting with B, lastname starting with M,
 * city 'Praha' birthdate before 1991 and date of hire after 2018.
 */
public class C_Query7_multi_filter implements Query {
    @Override
    public void runQuery(MongoDatabase db) {

        MongoCollection<Document> employees = db.getCollection("Employees");

        List<Document> results = employees.aggregate(List.of(

                // order can be optimized internally by Mongo, so it does not matter much
                new Document("$match",
                        new Document("firstName",
                                new Document("$regex", "^B")
                        )
                                .append("city", "Prague")
                                .append("birthDate",
                                        new Document("$lt", LocalDate.of(1991, 1,1))
                                )
                                .append("hireDate",
                                        new Document("$gt", LocalDate.of(2019, 1, 1))
                                )
                ),

                new Document("$project",
                        new Document("_id", 0)
                                .append("firstName", 1)
                                .append("lastName", 1)
                )

        ), Document.class).into(new ArrayList<>());

        for (Document row : results) {
            String firstName = row.getString("firstName");
            String lastName = row.getString("lastName");

            System.out.println(firstName + " " + lastName);
        }
    }
}
