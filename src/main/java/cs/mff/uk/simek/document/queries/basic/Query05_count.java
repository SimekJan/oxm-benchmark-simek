package cs.mff.uk.simek.document.queries.basic;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import cs.mff.uk.simek.document.northwind.Employee;
import cs.mff.uk.simek.document.queries.Query;
import org.bson.Document;

import java.util.ArrayList;
import java.util.List;

import static com.mongodb.client.model.Accumulators.sum;
import static com.mongodb.client.model.Aggregates.group;

/*
    Count the number of employees per city.
 */
public class Query05_count implements Query {

    @Override
    public void runQuery(MongoDatabase db) {

        MongoCollection<Employee> employees = db.getCollection("Employees", Employee.class);

        List<Document> result = employees.aggregate(List.of(
                group("$city", sum("count", 1))
        ), Document.class).into(new ArrayList<>());

        for (Document doc : result) {
            System.out.println(doc.get("_id") + ": " + doc.getInteger("count"));
        }
    }
}
