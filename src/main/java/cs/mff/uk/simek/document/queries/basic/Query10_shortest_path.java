package cs.mff.uk.simek.document.queries.basic;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Sorts;
import cs.mff.uk.simek.document.northwind.Supplier;
import cs.mff.uk.simek.document.queries.Query;
import org.bson.Document;

import java.util.ArrayList;
import java.util.List;

import static com.mongodb.client.model.Aggregates.*;
import static com.mongodb.client.model.Filters.eq;
import static com.mongodb.client.model.Projections.*;

/**
 * Find the shortest path between two given suppliers
 */
public class Query10_shortest_path implements Query {
    @Override
    public void runQuery(MongoDatabase db) {
        MongoCollection<Supplier> suppliers = db.getCollection("Suppliers", Supplier.class);

        List<Document> result = suppliers.aggregate(List.of(
                match(eq("companyName", "Epsilon")),
                new Document("$graphLookup",
                        new Document("from", "Suppliers")
                                .append("startWith", "$suppliedBy")
                                .append("connectFromField", "suppliedBy")
                                .append("connectToField", "_id")
                                .append("as", "reachable")
                                .append("depthField", "depth")
                                .append("maxDepth", 10) // safety limit
                ),
                unwind("$reachable"),
                match(eq("reachable.companyName", "Alpha")),
                sort(Sorts.ascending("reachable.depth")),
                limit(1),
                project(fields(
                        include("companyName"),
                        computed("target", "$reachable.companyName"),
                        computed("depth", "$reachable.depth")
                ))
        ), Document.class).into(new ArrayList<>());

        for (Document doc: result) {
            System.out.println(doc.toJson());
        }

        System.out.println("Note that depth starts as 0 for the first neighbour.");
    }
}

/*
    TODO: neexistuje přímo nejkratší cesta
            tohle bere všechny, a poté vyfiltruje tu, co chceme
            navíc neukládá cestu, ale jen hloubku
*/
