package cs.mff.uk.simek.document.queries_v2.basic;

import com.mongodb.client.MongoCollection;
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
 * Find all Customers without an order (all customers - (diff) customer_ids in orders)
 */
public class Query14_diff implements DocumentQuery<QX_No_Params> {

    @Override
    public void run(QX_No_Params params) {

        MongoCollection<Customer> customers = db.getCollection("Customers", Customer.class);

        List<Document> result = customers.aggregate(List.of(
                lookup("Orders", "customerId", "customer", "orders"),
                match(new Document("orders.0", new Document("$exists", false))),
                project(fields(include("companyName")))
        ), Document.class).into(new ArrayList<>());

        for (Document doc: result) {
            System.out.println(doc.get("companyName"));
        }
    }
}
