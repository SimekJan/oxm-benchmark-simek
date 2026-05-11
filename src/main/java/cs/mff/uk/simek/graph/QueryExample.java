package cs.mff.uk.simek.graph;

import cs.mff.uk.simek.graph.queries.Query;
import cs.mff.uk.simek.graph.queries.basic.*;
import cs.mff.uk.simek.graph.queries.complex.*;
import org.neo4j.ogm.session.Session;

public class QueryExample {

    public static void main(String[] args) {

        Session session = Neo4jSessionManager.getSession();

        Query q = new Query17_distinct();

        q.perform(session);
    }
}
