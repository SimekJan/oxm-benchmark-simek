package cs.mff.uk.simek.document.queries.basic;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import cs.mff.uk.simek.document.northwind.Supplier;
import cs.mff.uk.simek.document.queries.Query;
import org.bson.Document;

import java.util.ArrayList;
import java.util.List;

/*
    Find all direct and indirect connections between suppliers (max depth = 2).
 */
public class Query09_neighbours implements Query {
    @Override
    public void runQuery(MongoDatabase db) {

        MongoCollection<Supplier> suppliers = db.getCollection("Suppliers", Supplier.class);

        List<Document> result = suppliers.aggregate(List.of(
                new Document("$graphLookup",
                        new Document("from", "Suppliers")
                                .append("startWith", "$_id")
                                .append("connectFromField", "_id")
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
