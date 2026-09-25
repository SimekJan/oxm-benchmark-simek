package cs.mff.uk.simek.document.queries_v2.basic;

import com.mongodb.client.MongoCollection;
import cs.mff.uk.simek.document.northwind.Employee;
import cs.mff.uk.simek.document.queries_v2.DocumentQuery;
import cs.mff.uk.simek.query_params.params.QX_No_Params;
import org.bson.Document;

import java.util.ArrayList;
import java.util.List;

import static com.mongodb.client.model.Aggregates.*;
import static com.mongodb.client.model.Projections.*;

/**
 * Join customers and employees on city (non-indexed)
 */
public class Query08_join_non_indexed implements DocumentQuery<QX_No_Params> {

    @Override
    public void run(QX_No_Params params) {
        MongoCollection<Employee> employees = db.getCollection("Employees", Employee.class);

        List<Document> result = employees.aggregate(List.of(
                lookup("Customers",       // from collection
                        "city",                 // local field
                        "city",                 // foreign field
                        "customer"),            // output array field
                unwind("$customer"),   // INNER JOIN behavior
                project(fields(
                        include("firstName", "lastName", "city"),
                        computed("companyName", "$customer.companyName")
                ))
        ), Document.class).into(new ArrayList<>());

        for (Document doc : result) {
            System.out.println(doc.get("firstName") + " " + doc.get("lastName") + ": " +
                    doc.get("companyName") + " in " + doc.get("city"));
        }
    }
}
