package cs.mff.uk.simek.document.queries_v2.basic;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import cs.mff.uk.simek.document.northwind.Supplier;
import cs.mff.uk.simek.document.queries_v2.DocumentQuery;
import cs.mff.uk.simek.query_params.params.QX_No_Params;
import org.bson.Document;

import java.util.ArrayList;
import java.util.List;

/**
 * Find all direct and indirect connections between suppliers (max depth = 2).
 */
public class Query09_neighbours implements DocumentQuery<QX_No_Params> {

    @Override
    public void run(QX_No_Params params) {

        MongoCollection<Supplier> suppliers = db.getCollection("Suppliers", Supplier.class);

        List<Document> result = suppliers.aggregate(List.of(
                new Document("$graphLookup",
                        new Document("from", "Suppliers")
                                .append("startWith", "$supplierId")
                                .append("connectFromField", "supplierId")
                                .append("connectToField", "suppliedBy")
                                .append("as", "path")
                                .append("maxDepth", 2)
                                .append("depthField", "depth")
                ),
                new Document("$unwind", "$path"),
                new Document("$project",
                        new Document("fromSupplier", "$companyName")
                                .append("toSupplier", "$path.companyName")
                                .append("depth", "$path.depth")
                )
        ), Document.class).into(new ArrayList<>());

        for (Document doc: result) {
            System.out.println(doc.get("fromSupplier") + "\t to \t" + doc.get("toSupplier") + "\t with depth: " + doc.get("depth"));
        }
    }
}
