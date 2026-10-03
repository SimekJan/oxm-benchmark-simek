package cs.mff.uk.simek.graph.queries;

import cs.mff.uk.simek.graph.Neo4jSessionManager;
import org.neo4j.ogm.session.Session;

public interface GraphQuery<P> {

    Session session = Neo4jSessionManager.getSession();

    void run(P params);
}
