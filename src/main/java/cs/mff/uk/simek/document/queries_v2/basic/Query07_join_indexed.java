package cs.mff.uk.simek.document.queries_v2.basic;

import com.mongodb.client.MongoCollection;
import cs.mff.uk.simek.document.northwind.Order;
import cs.mff.uk.simek.document.queries_v2.DocumentQuery;
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
                        "employeeId",           // foreign field
                        "employee")             // output array field
        ), Document.class).into(new ArrayList<>());

        System.out.println("Orders - Employee join results: " + result.size());
        // for (Document doc : result) {
        //    List<Document> emp = (List<Document>) doc.get("employee");
        //    System.out.println("Order " + doc.getLong("orderId") + " -> " + emp.getFirst().get("firstName") + " " + emp.getFirst().get("lastName"));
        // }
    }
}
