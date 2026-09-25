package cs.mff.uk.simek.document.queries_v2.basic;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Sorts;
import cs.mff.uk.simek.document.northwind.Product;
import cs.mff.uk.simek.document.queries_v2.DocumentQuery;
import cs.mff.uk.simek.query_params.params.QX_No_Params;

import java.util.ArrayList;
import java.util.List;

import static com.mongodb.client.model.Aggregates.sort;

/**
 * Sort products based on unit price
 * (not indexed column)
 */
public class Query15_sorting implements DocumentQuery<QX_No_Params> {

    @Override
    public void run(QX_No_Params params) {
        MongoCollection<Product> products = db.getCollection("Products", Product.class);

        List<Product> results = products.aggregate(List.of(
                sort(Sorts.ascending("unitPrice"))
        ), Product.class).into(new ArrayList<>());

        for (Product p: results) {
            System.out.println(p.getProductName() + " " + p.getUnitPrice());
        }
    }
}
