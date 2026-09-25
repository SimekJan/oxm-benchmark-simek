package cs.mff.uk.simek.document.queries_v2.basic;

import com.mongodb.client.MapReduceIterable;
import com.mongodb.client.MongoCollection;
import cs.mff.uk.simek.document.northwind.Product;
import cs.mff.uk.simek.document.queries_v2.DocumentQuery;
import cs.mff.uk.simek.query_params.params.QX_No_Params;
import org.bson.Document;

/**
 * Number of products by supplier (one order or more)
 */
public class Query18_map_reduce implements DocumentQuery<QX_No_Params> {

    @Override
    public void run(QX_No_Params params) {

        MongoCollection<Product> products = db.getCollection("Products", Product.class);

        String mapFunction = "function() { emit(this.supplier, 1); }";
        String reduceFunction = "function(key, values) { return Array.sum(values); }";

        @Deprecated
        MapReduceIterable<Document> result = products.mapReduce(mapFunction, reduceFunction, Document.class);

        System.out.println("MapReduce: " + result);
        // result.forEach( doc ->
        //    System.out.println(doc.get("_id") + " -> " + doc.getDouble("value"))
        // );
    }
}

/*
    map-reduce is deprecated in MongoDB
    documentation advises to use aggregation instead
 */