package cs.mff.uk.simek.document.queries.basic;

import com.mongodb.client.MongoDatabase;
import cs.mff.uk.simek.document.queries.Query;
/*
     Number of products by supplier (one order or more)
 */
public class Query18_map_reduce implements Query {
    @Override
    public void runQuery(MongoDatabase db) {

    }
}

/*
    TODO: map-reduce je deprecated v MongoDB
            dokumentace radí použít agregaci, ale to nechceme
 */