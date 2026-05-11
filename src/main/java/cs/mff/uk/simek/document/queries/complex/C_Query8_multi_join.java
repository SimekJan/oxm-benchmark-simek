package cs.mff.uk.simek.document.queries.complex;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import cs.mff.uk.simek.document.queries.Query;
import org.bson.Document;

import java.util.ArrayList;
import java.util.List;

/*
 * Join Customers, Employees, Orders, Products and Suppliers.
 */
public class C_Query8_multi_join implements Query {
    @Override
    public void runQuery(MongoDatabase db) {

        MongoCollection<Document> orders = db.getCollection("Orders");

        List<Document> results = orders.aggregate(List.of(

                new Document("$lookup",
                        new Document("from", "Customers")
                                .append("localField", "customer")
                                .append("foreignField", "_id")
                                .append("as", "customer")
                ),
                new Document("$unwind", "$customer"),

                new Document("$lookup",
                        new Document("from", "Employees")
                                .append("localField", "employee")
                                .append("foreignField", "_id")
                                .append("as", "employee")
                ),
                new Document("$unwind", "$employee"),

                new Document("$lookup",
                        new Document("from", "Products")
                                .append("localField", "products")
                                .append("foreignField", "_id")
                                .append("as", "products")
                ),

                new Document("$unwind", "$products"),

                new Document("$lookup",
                        new Document("from", "Suppliers")
                                .append("localField", "products.supplier")
                                .append("foreignField", "_id")
                                .append("as", "supplier")
                ),
                new Document("$unwind", "$supplier"),

                new Document("$project",
                        new Document("_id", 0)
                                .append("customerName", "$customer.companyName")
                                .append("employeeFirstName", "$employee.firstName")
                                .append("orderId", "$orderId")
                                .append("productName", "$products.productName")
                                .append("supplierName", "$supplier.companyName")
                )

        ), Document.class).into(new ArrayList<>());

        for (Document row : results) {
            System.out.println(
                    row.getString("customerName") + " " +
                            row.getString("employeeFirstName") + " " +
                            row.get("orderId") + " " +
                            row.getString("productName") + " " +
                            row.getString("supplierName")
            );
        }
    }
}
