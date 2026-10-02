package cs.mff.uk.simek.document.queries.basic;

import com.mongodb.client.MongoCollection;
import cs.mff.uk.simek.document.northwind.Order;
import cs.mff.uk.simek.document.queries.DocumentQuery;
import cs.mff.uk.simek.query_params.params.QX_No_Params;
import org.bson.Document;

import java.util.ArrayList;
import java.util.List;

import static com.mongodb.client.model.Aggregates.lookup;

/**
 * Join orders with employees on employee ID (indexed).
 */
public class Query07_join_indexed implements DocumentQuery<QX_No_Params> {

    @Override
    public void run(QX_No_Params params) {
        MongoCollection<Order> orders = db.getCollection("Orders", Order.class);

        List<Document> result = orders.aggregate(List.of(
            lookup("Employees",       // from collection
                "employee",             // local field
                "_id",                  // foreign field
                "employee")             // output array field
        ), Document.class).into(new ArrayList<>());

        System.out.println("------------Mongo-Q7-------------");
        System.out.println("Orders - Employee join results (indexed): " + result.size());
    }
}
