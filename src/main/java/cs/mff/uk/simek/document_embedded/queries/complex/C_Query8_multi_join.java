package cs.mff.uk.simek.document_embedded.queries.complex;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import cs.mff.uk.simek.document_embedded.queries.Query;
import org.bson.Document;

import java.util.ArrayList;
import java.util.List;

/*
 * Join Customers, Employees, Orders, Products and Suppliers.
 */
public class C_Query8_multi_join implements Query {
    @Override
    public void runQuery(MongoDatabase db) {

        // TODO: pro tento design embeddingu nám bohužel neušetří ani jeden lookup
    }
}
