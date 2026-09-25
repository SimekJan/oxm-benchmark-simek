package cs.mff.uk.simek.document.queries_v2.basic;

import com.mongodb.client.MongoCollection;
import cs.mff.uk.simek.document.northwind.Customer;
import cs.mff.uk.simek.document.queries_v2.DocumentQuery;
import cs.mff.uk.simek.query_params.params.QX_No_Params;
import org.bson.Document;

import java.util.ArrayList;
import java.util.List;

import static com.mongodb.client.model.Aggregates.group;

/**
 * Distinct customer cities
 */
public class Query17_distinct implements DocumentQuery<QX_No_Params> {

    @Override
    public void run(QX_No_Params params) {

        MongoCollection<Customer> customers = db.getCollection("Customers", Customer.class);

        List<Document> result = customers.aggregate(List.of(
                group("$city")
        ), Document.class).into(new ArrayList<>());

        for (Document doc : result) {
            System.out.println(doc.getString("_id"));
        }
    }
}
