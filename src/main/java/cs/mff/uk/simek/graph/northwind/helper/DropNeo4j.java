package cs.mff.uk.simek.graph.northwind.helper;

import cs.mff.uk.simek.graph.Neo4jSessionManager;
import org.neo4j.ogm.session.Session;

import java.util.Collections;

public class DropNeo4j {
    public static void main(String[] args) {
        Session session = Neo4jSessionManager.getSession();
        session.query("MATCH (n) DETACH DELETE n", Collections.emptyMap());
    }
}
