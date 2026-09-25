package cs.mff.uk.simek.document.queries_v2.basic;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import cs.mff.uk.simek.document.northwind.Customer;
import cs.mff.uk.simek.document.queries_v2.DocumentQuery;
import cs.mff.uk.simek.query_params.params.QX_No_Params;
import org.bson.Document;

import java.util.ArrayList;
import java.util.List;

import static com.mongodb.client.model.Aggregates.*;
import static com.mongodb.client.model.Projections.fields;
import static com.mongodb.client.model.Projections.include;

/**
 * Find all cities in both Customers and Suppliers
 */
public class Query13_intersect implements DocumentQuery<QX_No_Params> {

    @Override
    public void run(QX_No_Params params, MongoDatabase db) {
        MongoCollection<Customer> customers = db.getCollection("Customers", Customer.class);

        List<Document> result = customers.aggregate(List.of(
                project(fields(include("city"))),
                lookup("Suppliers", "city", "city", "match"),
                match(new Document("match.0", new Document("$exists", true))),
                group("$city")
        ), Document.class).into(new ArrayList<>());

        for (Document doc: result) {
            System.out.println(doc.get("_id"));
        }
    }
}
